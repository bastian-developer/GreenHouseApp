package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etEmail, etPhoto;
    Button btnEditProfile;
    String id;
    RequestQueue requestQueue;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        requestQueue = Volley.newRequestQueue(UserActivity.this);

        initUI();

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

            editProfile();

        }

    }

    private void editProfile() {
        Toast.makeText(UserActivity.this, "xd", Toast.LENGTH_SHORT).show();

    }



}