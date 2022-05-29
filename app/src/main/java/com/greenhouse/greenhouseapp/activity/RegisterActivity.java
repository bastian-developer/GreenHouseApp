package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.greenhouse.greenhouseapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etEmail, etAddress;
    Button btnCreate, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

            //createUser(name, email, password, address);

            sendToLogIn();

        } else if (id == R.id.btnLogIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void sendToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}