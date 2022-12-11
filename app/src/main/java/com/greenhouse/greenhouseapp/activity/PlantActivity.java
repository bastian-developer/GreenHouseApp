package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.Connection;

import java.util.HashMap;
import java.util.Map;

public class PlantActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etType, etOrigin, etPhotos, etWaterSpent,etTemperature, etHumidity,etWater, etLight;;
    Button btnCreatePlant;
    String userId;

    RequestQueue requestQueue;

    private static final String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/savePlant.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        Bundle extras = getIntent().getExtras();
        userId = extras.getString("id");
        //Toast.makeText(PlantActivity.this, userId, Toast.LENGTH_SHORT).show();

        initUI();

        btnCreatePlant.setOnClickListener(this);

    }

    private void initUI(){

        etName = findViewById(R.id.etName);
        etType = findViewById(R.id.etType);
        etOrigin = findViewById(R.id.etOrigin);
        //etPhotos = findViewById(R.id.etPhotos);

        etWaterSpent = findViewById(R.id.etWaterSpent);
        etTemperature = findViewById(R.id.etTemperature);
        etHumidity = findViewById(R.id.etHumidity);
        etWater = findViewById(R.id.etWater);
        etLight = findViewById(R.id.etLight);

        btnCreatePlant = findViewById(R.id.btnCreatePlant);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnCreatePlant) {

            String name = etName.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String origin = etOrigin.getText().toString().trim();

            String temperature = etTemperature.getText().toString().trim();
            String humidity = etHumidity.getText().toString().trim();
            String water = etWater.getText().toString().trim();
            String light = etLight.getText().toString().trim();

            //String photos = etPhotos.getText().toString().trim();

            //HARDCODE
            String photos = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/user_uploads/cabbage.png";

            createPlant(name, type, origin, photos, "0", temperature, humidity, water,light);
            sendToMenu();

        }

    }

    public void sendToMenu() {
        Intent i = new Intent(PlantActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userId);
        i.putExtras(sendData);
        startActivity(i);
    }

    private void createPlant(final String name, final String type, final String origin, final String photos, final String waterSpent, final String temperature, final String humidity, final String water, final String light) {

        requestQueue = Volley.newRequestQueue(PlantActivity.this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PlantActivity.this, "Plant Created", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String e = error.toString();
                        Toast.makeText(PlantActivity.this, e, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            //Hashmap URL ENCODE
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("name", name);
                params.put("type", type);
                params.put("origin", origin);
                params.put("photos", photos);
                params.put("waterSpent", waterSpent);
                params.put("temperature", temperature);
                params.put("humidity", humidity);
                params.put("water", water);
                params.put("light", light);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        sendToMenu();
    }
}