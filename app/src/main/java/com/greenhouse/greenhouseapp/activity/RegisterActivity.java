package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etEmail, etAddress;
    Button btnCreate, btnLogin;

    RequestQueue requestQueue;


    private static final String URLDB = "http://192.168.0.3/greenhousedb/saveuser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        initUI();

        btnCreate.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    private void initUI(){

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);

        btnCreate = findViewById(R.id.btnCreateUser);
        btnLogin = findViewById(R.id.btnLogIn);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnCreateUser) {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            createUser(name, email, password, address);

            sendToLogIn();

        } else if (id == R.id.btnLogIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    private void createUser(final String name, final String email, final String password, final String address) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, "BKN", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String e = error.toString();

                        Toast.makeText(RegisterActivity.this, e, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            //Hashmap URL ENCODE
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("address", address);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void sendToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}