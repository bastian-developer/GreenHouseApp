package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.Connection;
import com.greenhouse.greenhouseapp.controller.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPlantActivity extends AppCompatActivity implements View.OnClickListener {

    String plantID, userID;
    ImageView image;
    Button btnEditPlant, bntUploadImage;
    EditText etName, etType, etOrigin, etWaterSpent,etTemperature, etHumidity,etWater, etLight;
    Bitmap bitmap, bitmapIcon;
    RequestQueue requestQueue;

    public static final String UPLOAD_URL = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/savePlantImage.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plant);

        requestQueue = Volley.newRequestQueue(EditPlantActivity.this);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("id");
        plantID = extras.getString("plantId");

        initUI();

        searchPlant();

        btnEditPlant.setOnClickListener(this);
        bntUploadImage.setOnClickListener(this);
    }
    private void initUI() {
        etName = findViewById(R.id.etName);
        etType = findViewById(R.id.etType);
        etOrigin = findViewById(R.id.etOrigin);

        etWaterSpent = findViewById(R.id.etWaterSpent);
        etTemperature = findViewById(R.id.etTemperature);
        etHumidity = findViewById(R.id.etHumidity);
        etWater = findViewById(R.id.etWater);
        etLight = findViewById(R.id.etLight);

        btnEditPlant = findViewById(R.id.btnEditPlant);
        bntUploadImage = findViewById(R.id.btnUploadImage);
        image = findViewById(R.id.image);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnEditPlant) {

            String name = etName.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String origin = etOrigin.getText().toString().trim();

            String temperature = etTemperature.getText().toString().trim();
            String humidity = etHumidity.getText().toString().trim();
            String water = etWater.getText().toString().trim();
            String light = etLight.getText().toString().trim();

            editPlant(name, type, origin, temperature, humidity, water, light);
            sendToPlantList();
            Toast.makeText(this, "Plant Updated", Toast.LENGTH_SHORT).show();



        } else if (id == R.id.btnUploadImage) {

            chooseFile();

        }

    }

    private void chooseFile(){
        Intent fileIntent = new Intent();
        fileIntent.setType("image/*");
        fileIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(fileIntent, "Select Picture"), 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null ) {

            Uri filepath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            uploadImage();
        }
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditPlantActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("userId", userID);
                data.put("plantId", plantID);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(imageByteArray);
        return encodedImage;
    }

    private void editPlant(final String name, final String type, final String origin, final String temperature, final String humidity, final String water, final String light) {
        String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/editPlant.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditPlantActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            //nullable
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", plantID);
                params.put("name", name);
                params.put("type", type);
                params.put("origin", origin);
                params.put("temperature", temperature);
                params.put("humidity", humidity);
                params.put("water", water);
                params.put("light", light);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void searchPlant() {
        String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/searchPlant.php?id=" + plantID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLDB,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        String name, type, origin, photo, temperature, humidity, water, light, waterSpent;
                        try{
                            name = response.getString("name");
                            type = response.getString("type");
                            origin = response.getString("origin");
                            photo = response.getString("photos");
                            waterSpent = response.getString("waterSpent");
                            temperature = response.getString("temperature");
                            humidity = response.getString("humidity");
                            water = response.getString("water");
                            light = response.getString("light");

                            //Toast.makeText(EditPlantActivity.this, name, Toast.LENGTH_SHORT).show();


                            etName.setText(name);
                            etType.setText(type);
                            etOrigin.setText(origin);

                            new GetImageFromUrl(image).execute(photo);

                            etWaterSpent.setText(waterSpent);
                            etTemperature.setText(temperature);
                            etHumidity.setText(humidity);
                            etWater.setText(water);
                            etLight.setText(light);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditPlantActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        ImageView imgV;

        public GetImageFromUrl(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {

            String urldisplay = url[0];

            bitmapIcon = null;

            try {
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmapIcon = BitmapFactory.decodeStream(srt);
            } catch (Exception e) {

            }

            return bitmapIcon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgV.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        sendToPlantList();
    }

    public void sendToPlantList() {
        Intent i = new Intent(EditPlantActivity.this, PlantListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userID);
        i.putExtras(sendData);
        startActivity(i);
        finish();
    }
}