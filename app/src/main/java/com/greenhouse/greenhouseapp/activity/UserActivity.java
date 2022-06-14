package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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
import com.greenhouse.greenhouseapp.controller.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etEmail, etPhoto;
    Button btnEditProfile, bntUploadImage;
    RequestQueue requestQueue;
    String AES = "AES";
    String userID;
    Bitmap bitmap, bitmapIcon;
    CircleImageView profileImage;

    //IMAGE
    public static final String UPLOAD_URL = "http://192.168.0.3/greenhousedb/saveProfileImage.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        requestQueue = Volley.newRequestQueue(UserActivity.this);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("id");
        Toast.makeText(UserActivity.this, userID, Toast.LENGTH_SHORT).show();

        initUI();

        searchUser();

        btnEditProfile.setOnClickListener(this);
        bntUploadImage.setOnClickListener(this);

    }

    private void initUI() {
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        //etPhoto = findViewById(R.id.etPhoto);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        bntUploadImage = findViewById(R.id.btnUploadImage);
        profileImage = findViewById(R.id.profileImage);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnEditProfile) {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            //String photo = etPhoto.getText().toString().trim();

            editProfile(name, email, password);
            sendToMenu();

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
                profileImage.setImageBitmap(bitmap);
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
                loading = ProgressDialog.show(UserActivity.this, "Uploading...", null,true,true);
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

    private void editProfile(final String name, final String email, final String password) {
        String URLDB = "http://192.168.0.3/greenhousedb/editUser.php";
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

                    }
                }
        ){
            //nullable
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", userID);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                //params.put("photo", photo);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void searchUser() {
        String URLDB = "http://192.168.0.3/greenhousedb/searchUser.php?id=" + userID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLDB,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        String name, email, password, photo;
                        try{
                            name = response.getString("name");
                            email = response.getString("email");
                            password = response.getString("password");
                            photo = response.getString("photo");



                            etName.setText(name);
                            etEmail.setText(email);
                            etPassword.setText(password);
                            //etPhoto.setText(photo);

                            //Hardcode
                            //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.image);
                            //profileImage.setImageBitmap(icon);

                            new GetImageFromUrl(profileImage).execute(photo);

                        } catch (JSONException e) {
                            e.printStackTrace();
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

        CircleImageView imgV;

        public GetImageFromUrl(CircleImageView imgV) {
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

    public void sendToMenu() {
        Intent i = new Intent(UserActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userID));
        i.putExtras(sendData);
        startActivity(i);
    }

}