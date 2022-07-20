package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greenhouse.greenhouseapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class statusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DEBUG_SA";

    String userId;
    TextView tvAirHumidity, tvSoilHumidity, tvTemperature;
    Button btnConnect;
    Spinner spinnerDevices;

    boolean connectionSwitch = false;

    BluetoothAdapter BTAdapter = null;
    Set<BluetoothDevice> BTPairedDevices = null;

    BluetoothDevice BTDevice = null;

    BluetoothSocket BTSocket = null;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

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

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnConnect) {

            Log.d(TAG, "BTN Connect pressed");

            if (connectionSwitch == false) {
                if (spinnerDevices.getSelectedItemPosition() == 0) {

                    Toast.makeText(getApplicationContext(), "Select Device", Toast.LENGTH_SHORT).show();

                }

                String selectedDeviceName = spinnerDevices.getSelectedItem().toString();

                Log.d(TAG, "Selected device: " + selectedDeviceName);


                for (BluetoothDevice BTDev : BTPairedDevices) {

                    if (selectedDeviceName.equals(BTDev.getName())) {

                        BTDevice = BTDev;

                        try {

                            Log.d(TAG, "Creating Socket, app UUID: " + MY_UUID);

                            BTSocket = BTDevice.createRfcommSocketToServiceRecord(MY_UUID);

                            Log.d(TAG, "Connecting to device");

                            //APP will freeze until connected
                            BTSocket.connect();
                            Log.d(TAG, "Connected");
                            Toast.makeText(getApplicationContext(), "Connected to " + selectedDeviceName, Toast.LENGTH_SHORT).show();
                            btnConnect.setText("Disconnect");
                            connectionSwitch = true;

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "Exception: " + e);
                            connectionSwitch = false;
                        }

                    }

                }

                Log.d(TAG, "Selected device UUID: " + BTDevice.getAddress());

            } else {

                connectionSwitch = false;
                try {
                    BTSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


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