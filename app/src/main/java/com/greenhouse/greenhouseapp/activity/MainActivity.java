package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        btnLogOut.setOnClickListener(this);
    }

    private void initUI(){

        btnLogOut = findViewById(R.id.btnLogout);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnLogout) {

            logout();

        }
    }

    public void logout() {
        //remove session and open log in screen
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        sendTologIn();
    }

    public void sendTologIn() {

        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        //Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(i, b);
        startActivity(i);
    }

}