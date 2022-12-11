package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class StatisticsActivity extends AppCompatActivity {

    String userID;

    TextView etTotalPlantsUser,
            etTotalWaterUser,
            etAverageWaterUser,
            etMinWaterUser,
            etMaxWaterUser,
            etTotalPlantsTotal,
            etTotalWaterTotal,
            etAverageWaterTotal,
            etMinWaterTotal,
            etMaxWaterTotal;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        requestQueue = Volley.newRequestQueue(StatisticsActivity.this);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("id");

        initUI();
        searchStatistics();
    }

    private void initUI() {

        etTotalPlantsUser = findViewById(R.id.etTotalPlantsUser);
        etTotalWaterUser = findViewById(R.id.etTotalWaterUser);
        etAverageWaterUser = findViewById(R.id.etAverageWaterUser);
        etMinWaterUser = findViewById(R.id.etMinWaterUser);
        etMaxWaterUser = findViewById(R.id.etMaxWaterUser);

        etTotalPlantsTotal = findViewById(R.id.etTotalPlantsTotal);
        etTotalWaterTotal = findViewById(R.id.etTotalWaterTotal);
        etAverageWaterTotal = findViewById(R.id.etAverageWaterTotal);
        etMinWaterTotal = findViewById(R.id.etMinWaterTotal);
        etMaxWaterTotal = findViewById(R.id.etMaxWaterTotal);

    }

    @Override
    public void onBackPressed() {
        sendToMenu();
    }

    public void sendToMenu() {
        Intent i = new Intent(StatisticsActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userID);
        i.putExtras(sendData);
        startActivity(i);
        finish();
    }

    public void searchStatistics() {

        searchTotalPlantsUser();

        searchTotalPlantsTotal();

    }

    private void searchTotalPlantsUser() {
        String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/searchTotalPlantsUser.php?id=" + userID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLDB,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        String totalPlantsUser;
                        try{
                            totalPlantsUser = response.getString("COUNT(*)");
                            etTotalPlantsUser.setText(totalPlantsUser);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StatisticsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void searchTotalPlantsTotal() {
        String URLDB = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/searchTotalPlantsTotal.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLDB,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        String totalPlantsTotal;
                        try{
                            totalPlantsTotal = response.getString("COUNT(*)");


                            etTotalPlantsTotal.setText(totalPlantsTotal);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StatisticsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}