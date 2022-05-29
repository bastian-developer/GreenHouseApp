package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;
import com.greenhouse.greenhouseapp.model.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void login(View view) {

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