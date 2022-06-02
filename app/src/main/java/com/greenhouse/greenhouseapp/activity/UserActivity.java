package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etEmail, etPhoto;
    Button btnEditProfile;
    RequestQueue requestQueue;
    String AES = "AES";
    String userID;

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


    }

    private void initUI() {
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhoto = findViewById(R.id.etPhoto);
        btnEditProfile = findViewById(R.id.btnEditProfile);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnEditProfile) {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String photo = etPhoto.getText().toString().trim();

            editProfile(name, email, password, photo);
            sendToMenu();

        }

    }

    private void editProfile(final String name, final String email, final String password, final String photo) {
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
                params.put("photo", photo);
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
                            etPhoto.setText(photo);

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

    public void sendToMenu() {
        Intent i = new Intent(UserActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userID));
        i.putExtras(sendData);
        startActivity(i);
    }

}