package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.greenhouse.greenhouseapp.R;

public class statusActivity extends AppCompatActivity {

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Bundle extras = getIntent().getExtras();
        userId = extras.getString("id");
    }

    @Override
    public void onBackPressed() {
        sendToMenu();
    }

    public void sendToMenu() {
        Intent i = new Intent(statusActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userId);
        i.putExtras(sendData);
        startActivity(i);
    }
}