package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greenhouse.greenhouseapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class statusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DEBUG_SA";

    //APP VARIABLES
    String userId;
    TextView tvAirHumidity, tvSoilHumidity, tvTemperature;
    Button btnConnect, btnSend;
    Spinner spinnerDevices;

    //BT VARIABLES
    BluetoothAdapter BTAdapter = null;
    Set<BluetoothDevice> BTPairedDevices = null;
    BluetoothDevice BTDevice = null;
    BluetoothSocket BTSocket = null;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    boolean connectionSwitch = false;
    classBTInitDataCommunication cBTInitSendReceive =null;

    //BT STATES
    static public final int BT_CON_STATUS_NOT_CONNECTED     =0;
    static public final int BT_CON_STATUS_CONNECTING        =1;
    static public final int BT_CON_STATUS_CONNECTED         =2;
    static public final int BT_CON_STATUS_FAILED            =3;
    static public final int BT_CON_STATUS_CONNECTiON_LOST   =4;
    static public int iBTConnectionStatus = BT_CON_STATUS_NOT_CONNECTED;
    static final int BT_STATE_LISTENING            =1;
    static final int BT_STATE_CONNECTING           =2;
    static final int BT_STATE_CONNECTED            =3;
    static final int BT_STATE_CONNECTION_FAILED    =4;
    static final int BT_STATE_MESSAGE_RECEIVED     =5;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Log.d(TAG, "onCreate Start");

        //Retrieve User ID
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("id");

        //Setup UI
        initUI();
        btnConnect.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        //Get BT Paired Devices
        getBTPairedDevices();

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
        //tvSoilHumidity = findViewById(R.id.tvSoilHumidity);
        //tvTemperature = findViewById(R.id.tvTemperature);
        btnConnect = findViewById(R.id.btnConnect);
        btnSend = findViewById(R.id.btnSend);
        spinnerDevices = findViewById(R.id.spinnerDevices);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {

        //Get View
        int id = v.getId();

        //Connect Button
        if (id == R.id.btnConnect) {

            Log.d(TAG, "BTN Connect pressed");

            if (connectionSwitch == false) {

                //Validation
                if (spinnerDevices.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Select Device", Toast.LENGTH_SHORT).show();
                }

                //Get Selected Device
                String selectedDeviceName = spinnerDevices.getSelectedItem().toString();
                Log.d(TAG, "Selected device: " + selectedDeviceName);

                //Match selected device in paired devices list
                for (BluetoothDevice BTDev : BTPairedDevices) {

                    if (selectedDeviceName.equals(BTDev.getName())) {

                        //Get device and connect
                        BTDevice = BTDev;
                        cBluetoothConnect cBTConnect = new cBluetoothConnect(BTDevice);
                        cBTConnect.start();

                        //Why the app still works without this?
                        /*
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
                         */
                    }

                }

                Log.d(TAG, "Selected device UUID: " + BTDevice.getAddress());

            } else {

                //Disconnect from BT Device
                connectionSwitch = false;
                btnConnect.setText("Connect");
                try {
                    BTSocket.close();
                    Log.d(TAG, "Disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        //Button Send
        } else if (id == R.id.btnSend) {

            sendMessage("1", BTSocket);
            Log.d(TAG, "BTN SEND CLICKED");
            //Toast.makeText(getApplicationContext(), "OMG", Toast.LENGTH_SHORT).show();
        }

    }

    //Redirect to menu with user data
    @Override
    public void onBackPressed() {
        sendToMenu();
    }
    //
    public void sendToMenu() {
        Intent i = new Intent(statusActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userId);
        i.putExtras(sendData);
        startActivity(i);
    }

    //GET BT PAIRED DEVICES
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


    }

    //Connect to BT Method
    @SuppressLint("MissingPermission")
    public class cBluetoothConnect extends Thread {

        private BluetoothDevice device;

        public cBluetoothConnect (BluetoothDevice BTDevice) {

            Log.d(TAG, "class cBluetoothConnect started");

            device = BTDevice;

            try {
                BTSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            }catch (Exception e) {
                Log.d(TAG, "Exception: " + e);
            }

        }

        public void run() {
            try {
                BTSocket.connect();
                Message message = Message.obtain();
                message.what=BT_STATE_CONNECTED;
                handler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what=BT_STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
                Log.d(TAG, "Exception: " + e);
            }
        }

    }

    public class classBTInitDataCommunication extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private InputStream inputStream =null;
        private OutputStream outputStream=null;

        public classBTInitDataCommunication (BluetoothSocket socket)
        {
            Log.i(TAG, "classBTInitDataCommunication-start");

            bluetoothSocket=socket;


            try {
                inputStream=bluetoothSocket.getInputStream();
                outputStream=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "classBTInitDataCommunication-start, exp " + e.getMessage());
            }


        }

        public void run()
        {
            byte[] buffer=new byte[1024];
            int bytes;

            while (BTSocket.isConnected())
            {
                try {
                    bytes=inputStream.read(buffer);
                    handler.obtainMessage(BT_STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "BT disconnect from decide end, exp " + e.getMessage());
                    iBTConnectionStatus=BT_CON_STATUS_CONNECTiON_LOST;
                    try {
                        //disconnect bluetooth
                        Log.d(TAG, "Disconnecting BTConnection");
                        if(BTSocket!=null && BTSocket.isConnected())
                        {

                            BTSocket.close();
                        }
                        btnConnect.setText("Connect");
                        connectionSwitch = false;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //
    }

    Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what)
            {
                case BT_STATE_LISTENING:
                    Log.d(TAG, "BT_STATE_LISTENING");
                    break;
                case BT_STATE_CONNECTING:
                    iBTConnectionStatus = BT_CON_STATUS_CONNECTING;
                    btnConnect.setText("Connecting..");
                    Log.d(TAG, "BT_STATE_CONNECTING");
                    break;
                case BT_STATE_CONNECTED:

                    iBTConnectionStatus = BT_CON_STATUS_CONNECTED;

                    Log.d(TAG, "BT_CON_STATUS_CONNECTED");
                    btnConnect.setText("Disconnect");

                    classBTInitDataCommunication cBTInitSendReceive = new classBTInitDataCommunication(BTSocket);
                    cBTInitSendReceive.start();

                    connectionSwitch = true;
                    break;
                case BT_STATE_CONNECTION_FAILED:
                    iBTConnectionStatus = BT_CON_STATUS_FAILED;
                    Log.d(TAG, "BT_STATE_CONNECTION_FAILED");
                    connectionSwitch = false;
                    break;

                case BT_STATE_MESSAGE_RECEIVED:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    Log.d(TAG, "Message receive ( " + tempMsg.length() + " )  data : " + tempMsg);
                    tvAirHumidity.append(tempMsg);
                    break;

            }
            return true;
        }
    });


    @SuppressLint("MissingPermission")
    public void sendMessage(String sMessage, BluetoothSocket BTSocket)
    {
        //Instance send/receive class again?
        cBTInitSendReceive = new classBTInitDataCommunication(BTSocket);
        Log.d(TAG, "sendMessage function " + sMessage);
        if(BTSocket!= null && iBTConnectionStatus==BT_CON_STATUS_CONNECTED)
        {
            if(BTSocket.isConnected())
            {
                try {
                    cBTInitSendReceive.write(sMessage.getBytes());
                    tvSoilHumidity.append("\r\n-> " + sMessage);
                    Log.d(TAG, "sendMessage function " + sMessage);
                }
                catch (Exception exp)
                {

                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please connect to bluetooth", Toast.LENGTH_SHORT).show();
            tvSoilHumidity.append("\r\n Not connected to bluetooth");
        }
    }
}