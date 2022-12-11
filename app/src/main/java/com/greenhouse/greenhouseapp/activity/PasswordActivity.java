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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.Connection;

import java.util.HashMap;
import java.util.Map;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etPassword, etPassword2;
    RequestQueue requestQueue;
    Button btnChangePassword;
    String AES = "AES";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        requestQueue = Volley.newRequestQueue(PasswordActivity.this);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("id");

        initUI();

        btnChangePassword.setOnClickListener(this);

    }

    private void initUI() {

        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        btnChangePassword = findViewById(R.id.btnChangePassword);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnChangePassword) {

            String pass1 = etPassword.getText().toString().trim();
            String pass2 = etPassword2.getText().toString().trim();


            changePassword(pass1);
            Toast.makeText(this, "Password Updated", Toast.LENGTH_SHORT).show();

            sendToProfile();

        }

    }

    private void changePassword(final String password) {
        String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/editPassword.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //finish();
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
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        sendToProfile();
    }

    public void sendToProfile() {
        Intent i = new Intent(PasswordActivity.this, UserActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userID);
        i.putExtras(sendData);
        startActivity(i);
        finish();
    }
}