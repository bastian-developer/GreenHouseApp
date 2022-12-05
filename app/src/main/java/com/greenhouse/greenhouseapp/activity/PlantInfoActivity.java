package com.greenhouse.greenhouseapp.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import com.greenhouse.greenhouseapp.controller.GlobalPlantListAdapter;
import com.greenhouse.greenhouseapp.controller.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PlantInfoActivity extends AppCompatActivity implements View.OnClickListener{

    String plantID, userID;
    ImageView image;
    Button btnCopyPlant;
    TextView etName, etType, etOrigin, etWaterSpent,etTemperature, etHumidity,etWater, etLight;
    Bitmap bitmap, bitmapIcon;
    RequestQueue requestQueue;

    public static final String UPLOAD_URL = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/savePlantImage.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);

        requestQueue = Volley.newRequestQueue(PlantInfoActivity.this);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("id");
        plantID = extras.getString("plantId");

        initUI();

        searchPlant();

        btnCopyPlant.setOnClickListener(this);
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

        btnCopyPlant = findViewById(R.id.btnCopyPlant);
        image = findViewById(R.id.image);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnCopyPlant) {

            copyPlant();

        }

    }


    private void copyPlant() {

        requestQueue = Volley.newRequestQueue(PlantInfoActivity.this);

        String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/copyPlant.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PlantInfoActivity.this, "Added to My Plants", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(PlantInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", plantID);
                params.put("userId", userID);

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


                            etName.setText(name);
                            etType.setText(type);
                            etOrigin.setText(origin);

                            new PlantInfoActivity.GetImageFromUrl(image).execute(photo);

                            etWaterSpent.setText(waterSpent);
                            etTemperature.setText(temperature);
                            etHumidity.setText(humidity);
                            etWater.setText(water);
                            etLight.setText(light);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PlantInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

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
        sendToGlobalPlantList();
    }

    public void sendToGlobalPlantList() {
        Intent i = new Intent(PlantInfoActivity.this, GlobalPlantsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userID);
        i.putExtras(sendData);
        startActivity(i);
        finish();
    }
}
