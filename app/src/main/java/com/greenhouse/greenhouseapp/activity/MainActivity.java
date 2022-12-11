package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btnLogOut, btnProfile, btnPlant, btnStatus, btnPlants, btnGlobalPlants, btnStatistics;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();


        userId = extras.getString("id");

        initUI();

        btnLogOut.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnPlant.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
        btnPlants.setOnClickListener(this);
        btnGlobalPlants.setOnClickListener(this);
        btnStatistics.setOnClickListener(this);



        //Explicit Bluetooth Permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }
    }

    private void initUI(){

        btnLogOut = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);
        btnPlant = findViewById(R.id.btnCreatePlant);
        btnStatus = findViewById(R.id.btnStatus);
        btnPlants = findViewById(R.id.btnPlants);
        btnGlobalPlants = findViewById(R.id.btnGlobalPlants);
        btnStatistics = findViewById(R.id.btnStatistics);

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

        }else if (id == R.id.btnGlobalPlants) {

            sendToGlobalPlants(userId);

        }else if (id == R.id.btnStatistics) {

            sendToStatistics(userId);

        }

    }

    public void sendToStatistics(String userId) {

        Intent i = new Intent(MainActivity.this, StatisticsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userId));
        i.putExtras(sendData);
        startActivity(i);
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

    public void sendToGlobalPlants(String userId) {

        Intent i = new Intent(MainActivity.this, GlobalPlantsActivity.class);
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


        /*
        Toast.makeText(MainActivity.this, "xD", Toast.LENGTH_SHORT).show();
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userID2 = sessionManagement.getSession();
        if(userID2 != -1) {
            Toast.makeText(MainActivity.this, userID2, Toast.LENGTH_SHORT).show();
        } else {}

        */

        Intent i = new Intent(MainActivity.this, PlantListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(userId));
        i.putExtras(sendData);
        startActivity(i);


    }

}