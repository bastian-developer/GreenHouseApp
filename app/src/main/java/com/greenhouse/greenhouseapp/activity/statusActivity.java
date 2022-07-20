package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.greenhouse.greenhouseapp.R;

import java.util.ArrayList;
import java.util.Set;

public class statusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DEBUG_SA";

    String userId;
    TextView tvAirHumidity, tvSoilHumidity, tvTemperature;
    Button btnConnect;
    Spinner spinnerDevices;

    BluetoothAdapter BTAdapter = null;
    Set<BluetoothDevice> BTPairedDevices = null;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Log.d(TAG, "onCreate Start");

        //getBTPairedDevices();
        //populateSpinnerWithBTPairedDevices();

        Bundle extras = getIntent().getExtras();
        userId = extras.getString("id");

        initUI();


        btnConnect.setOnClickListener(this);

        //BT

        BTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (BTAdapter == null) {
            Log.e(TAG, "getBTPairedDevices, BTAdapter null");
        } else if (!BTAdapter.isEnabled()) {
            Log.e(TAG, "getBTPairedDevices, BT Not Enabled");
        }

        BTPairedDevices = BTAdapter.getBondedDevices();

        Log.e(TAG, "getBTPairedDevices, Paired Devices Count = " + BTPairedDevices.size());

        //Populate spinner

        ArrayList<String> allPairedDevices = new ArrayList<>();

        ArrayAdapter<String> arrayAdapterPairedDevices;

        allPairedDevices.add("Select Device");


        for (BluetoothDevice BTDev : BTPairedDevices) {

            Log.d(TAG, BTDev.getName() + ", " + BTDev.getAddress());

            allPairedDevices.add(BTDev.getName());

            Log.d(TAG, allPairedDevices.get(1));
            arrayAdapterPairedDevices = new ArrayAdapter<>(statusActivity.this,
                    R.layout.color_spinner_layout, allPairedDevices);
            arrayAdapterPairedDevices.setDropDownViewResource(R.layout.color_spinner_layout);
            spinnerDevices.setAdapter((arrayAdapterPairedDevices));

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume Start");

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause Start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy Start");
    }

    private void initUI() {
        tvAirHumidity = findViewById(R.id.tvAirHumidity);
        tvSoilHumidity = findViewById(R.id.tvSoilHumidity);
        tvTemperature = findViewById(R.id.tvTemperature);
        btnConnect = findViewById(R.id.btnConnect);
        spinnerDevices = findViewById(R.id.spinnerDevices);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnConnect) {

            Log.d(TAG, "BTN Connect pressed");

        }

    }

    @Override
    public void onBackPressed() {
        sendToMenu();
    }

    public void sendToMenu() {
        Intent i = new Intent(statusActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userId);
        i.putExtras(sendData);
        startActivity(i);
    }

    @SuppressLint("MissingPermission")
    public void getBTPairedDevices() {
        BTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (BTAdapter == null) {
            Log.e(TAG, "getBTPairedDevices, BTAdapter null");
        } else if (!BTAdapter.isEnabled()) {
            Log.e(TAG, "getBTPairedDevices, BT Not Enabled");
        }

        BTPairedDevices = BTAdapter.getBondedDevices();

        Log.e(TAG, "getBTPairedDevices, Paired Devices Count = " + BTPairedDevices.size());

        //Populate spinner

        ArrayList<String> allPairedDevices = new ArrayList<>();

        ArrayAdapter<String> arrayAdapterPairedDevices;

        allPairedDevices.add("Select Device");


        for (BluetoothDevice BTDev : BTPairedDevices) {

            Log.d(TAG, BTDev.getName() + ", " + BTDev.getAddress());

            allPairedDevices.add(BTDev.getName());

            Log.d(TAG, allPairedDevices.get(1));
            arrayAdapterPairedDevices = new ArrayAdapter<>(statusActivity.this,
                    R.layout.color_spinner_layout, allPairedDevices);
            arrayAdapterPairedDevices.setDropDownViewResource(R.layout.color_spinner_layout);
            spinnerDevices.setAdapter((arrayAdapterPairedDevices));

        }






        //allPairedDevices.add("Select Device");



        //final ArrayAdapter<String> arrayAdapterPairedDevices = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, allPairedDevices);
        //arrayAdapterPairedDevices.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        //spinnerDevices.setAdapter(arrayAdapterPairedDevices);

    }

    @SuppressLint("MissingPermission")
    public void populateSpinnerWithBTPairedDevices() {


    }
}