package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;
import com.greenhouse.greenhouseapp.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnRegister, btnLogIn;
    String email, password;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            email = extras.getString("email");
            password = extras.getString("password");
        }

        initUI();


        btnRegister.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }

    private void initUI(){


        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);


        btnRegister = findViewById(R.id.btnRegister);
        btnLogIn = findViewById(R.id.btnLogin);

    }

    @Override
    public void onClick(View v) {


        int id = v.getId();


        if (id == R.id.btnLogin) {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            login(email, password);

        } else if (id == R.id.btnRegister) {

            sendToRegister();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {

        //check if user is logged in
        //if user is logged in, move to mainActivity

        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();

        if(userID != -1) {
            //user is logged in, move to mainActivity
            sendToMenu();
        } else {
            //do nothing
        }
    }

    public void login(String email, String password) {
        String URLDB = "http://192.168.0.3/greenhousedb/login.php?email=" + email + "&password=" + password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLDB,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String email, password;
                        try{

                            email = response.getString("email");
                            password = response.getString("password");

                            User user = new User(email, password);

                            SessionManagement sessionManagement =  new SessionManagement(LoginActivity.this);
                            sessionManagement.saveSession(user);

                            etEmail.setText(email);
                            etPassword.setText(password);

                            Toast.makeText(LoginActivity.this, email+ " " + password, Toast.LENGTH_SHORT).show();

                            sendToMenu();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this, "Authentication Error", Toast.LENGTH_SHORT).show();


                    }
                }
        );

        requestQueue.add(jsonObjectRequest);

    }

    public void sendToMenu() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void sendToRegister() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}