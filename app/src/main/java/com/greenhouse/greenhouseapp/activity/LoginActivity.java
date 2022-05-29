package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;
import com.greenhouse.greenhouseapp.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnRegister, btnLogIn;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            login();

        } else if (id == R.id.btnRegister) {
            //Intent intent = new Intent(this, RegisterActivity.class);
            //startActivity(intent);
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

    public void login() {

        User user = new User(666, "Satan", "sa@tan.com", "lol", "hell");

        SessionManagement sessionManagement =  new SessionManagement(LoginActivity.this);
        sessionManagement.saveSession(user);

        sendToMenu();

    }

    public void sendToMenu() {

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        //Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(i, b);
        startActivity(i);
    }

}