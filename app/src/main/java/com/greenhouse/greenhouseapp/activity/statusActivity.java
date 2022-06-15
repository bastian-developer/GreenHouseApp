package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greenhouse.greenhouseapp.R;

public class statusActivity extends AppCompatActivity implements View.OnClickListener {

    String userId;
    TextView tvHumidity, tvTemperature;
    Button btnSwitch, btnGetValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Bundle extras = getIntent().getExtras();
        userId = extras.getString("id");

        initUI();

        btnSwitch.setOnClickListener(this);
        btnGetValues.setOnClickListener(this);

    }

    private void initUI() {
        tvHumidity = findViewById(R.id.tvHumidity);
        tvTemperature = findViewById(R.id.tvTemperature);
        btnSwitch = findViewById(R.id.btnSwitch);
        btnGetValues = findViewById(R.id.btnGetValues);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnSwitch) {

            Toast.makeText(statusActivity.this, "Switch", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.btnGetValues) {

            Toast.makeText(statusActivity.this, "Values", Toast.LENGTH_SHORT).show();

        }

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