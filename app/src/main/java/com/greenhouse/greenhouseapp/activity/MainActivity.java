package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btnLogOut, btnProfile, btnPlant, btnStatus, btnPlants;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();


        userId = extras.getString("id");


        Toast.makeText(MainActivity.this, userId, Toast.LENGTH_SHORT).show();


        initUI();

        btnLogOut.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnPlant.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
        btnPlants.setOnClickListener(this);
    }

    private void initUI(){

        btnLogOut = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);
        btnPlant = findViewById(R.id.btnCreatePlant);
        btnStatus = findViewById(R.id.btnStatus);
        btnPlants = findViewById(R.id.btnPlants);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnLogout) {

            logout();

        }else if (id == R.id.btnProfile) {

            sendToProfile(userId);

        }else if (id == R.id.btnCreatePlant) {

            sendToCreatePlant(userId);

        }else if (id == R.id.btnStatus) {

            sendToStatus(userId);

        }else if (id == R.id.btnPlants) {

            sendToPlants(userId);

        }



    }


    public void logout() {
        //remove session and open log in screen
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        sendTologIn();
    }

    public void sendTologIn() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void sendToProfile(String userId) {

        Intent i = new Intent(MainActivity.this, UserActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userId));
        i.putExtras(sendData);
        startActivity(i);
    }

    public void sendToCreatePlant(String userId) {

        Intent i = new Intent(MainActivity.this, PlantActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userId));
        i.putExtras(sendData);
        startActivity(i);
    }

    public void sendToStatus(String userId) {

        Intent i = new Intent(MainActivity.this, statusActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userId));
        i.putExtras(sendData);
        startActivity(i);
    }

    public void sendToPlants(String userId) {

        Toast.makeText(MainActivity.this, "xD", Toast.LENGTH_SHORT).show();

        /*
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userID2 = sessionManagement.getSession();
        if(userID2 != -1) {
            Toast.makeText(MainActivity.this, userID2, Toast.LENGTH_SHORT).show();
        } else {}



        Intent i = new Intent(MainActivity.this, PlantsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userId));
        i.putExtras(sendData);
        startActivity(i);
        */

    }

}