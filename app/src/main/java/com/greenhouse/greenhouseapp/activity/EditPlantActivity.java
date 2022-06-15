package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.greenhouse.greenhouseapp.R;

public class EditPlantActivity extends AppCompatActivity {

    String plantID, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plant);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("id");
        plantID = extras.getString("plantId");

        Toast.makeText(EditPlantActivity.this, plantID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        sendToPlantList();
    }

    public void sendToPlantList() {
        Intent i = new Intent(EditPlantActivity.this, PlantListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userID);
        i.putExtras(sendData);
        startActivity(i);
        finish();
    }
}