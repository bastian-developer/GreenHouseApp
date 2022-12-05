package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.Connection;
import com.greenhouse.greenhouseapp.controller.PlantListAdapter;
import com.greenhouse.greenhouseapp.model.Plant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlantListActivity extends AppCompatActivity {

    //a List of type plant for holding list items
    List<Plant> plantList;

    //the listview
    ListView listView;

    String userID;

    Boolean _switch = false;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        Bundle extras = getIntent().getExtras();


        userID = extras.getString("id");

        //initializing objects
        plantList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        //Hardcoding this lechugon to fix bug
        //plantList.add(new Plant(1,1, "Lechugon", "Fruit", "Asia", "http://"+ Connection.GLOBAL_IP + "/greenhousedb/user_uploads/cabbage.png"));
        //plantList.add(new Plant(1,1, "Lechugon", "Fruit", "Asia", "http://"+ Connection.GLOBAL_IP + "/greenhousedb/user_uploads/cabbage.png"));

        //Log.d("Plant 1: ", plantList.toString());

        //Fill list with user plants
        //searchPlants();

        //Log.d("Plant 2: ", plantList.toString());

        //Execute Async Tasks
        PlantListActivity.asyncTask asyncTask = new PlantListActivity.asyncTask();
        asyncTask.execute();

        //creating the adapter
        //PlantListAdapter adapter = new PlantListAdapter(PlantListActivity.this, R.layout.custom_list, plantList, userID);
        //attaching adapter to the listview
        //listView.setAdapter(adapter);
    }

    public void searchPlants() {
        requestQueue = Volley.newRequestQueue(PlantListActivity.this);
        String URL = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/populateUserPlants.php?userId=" + userID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        plantList.clear();

                        try {

                            JSONArray jsonArray = response.getJSONArray("plants");

                            //JSONObject plantObject = response.getJSONObject("plants");
                            //JSONArray songsArray = plantObject.toJSONArray();
                            //plantList.remove(0);

                            for (int i = 0; i<jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id, userId, name, type, origin, photos, waterSpent, temperature, humidity, water, light;

                                id = jsonObject.optString("id");
                                userId = jsonObject.optString("userId");
                                name = jsonObject.optString("name");
                                type = jsonObject.optString("type");
                                origin = jsonObject.optString("origin");
                                photos = jsonObject.optString("photos");

                                //Stats
                                waterSpent = jsonObject.optString("waterSpent");
                                temperature = jsonObject.optString("temperature");
                                humidity = jsonObject.optString("humidity");
                                water = jsonObject.optString("water");
                                light = jsonObject.optString("light");


                                Plant plantita = new Plant(Integer.parseInt(id),Integer.parseInt(userId), name, type, origin, photos, Integer.parseInt(waterSpent), Integer.parseInt(temperature), Integer.parseInt(humidity), Integer.parseInt(water), Integer.parseInt(light));

                                //plantList.add(new Plant(Integer.parseInt(id),Integer.parseInt(userId), name, type, origin, photos));

                                plantList.add(plantita);
                                Log.d("Plant: ", plantList.toString());

                                _switch = true;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("xd 1", "Plantlist Error: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("xd 2", "Plantlist Error: " + error);
                    }
                }

        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        sendToMenu();
    }

    public void sendToMenu() {
        Intent i = new Intent(PlantListActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle sendData = new Bundle();
        sendData.putString("id", userID);
        i.putExtras(sendData);
        startActivity(i);
    }

    //Async Task
    public void thread(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void _execute(){
        PlantListActivity.asyncTask asyncTask = new PlantListActivity.asyncTask();
        asyncTask.execute();
    }

    public class asyncTask extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 1; i <3; i++) {
                thread();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            _execute();


            if (_switch == false) {

                searchPlants();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        PlantListAdapter adapter = new PlantListAdapter(PlantListActivity.this, R.layout.custom_list, plantList, userID);
                        listView.setAdapter(adapter);
                    }
                }, 5000);   //5 seconds


            }


            //searchPlants();

            //Log.d("Plant 2: ", plantList.toString());

            //creating the adapter
            //PlantListAdapter adapter = new PlantListAdapter(PlantListActivity.this, R.layout.custom_list, plantList, userID);
            //attaching adapter to the listview
            //listView.setAdapter(adapter);
        }
    }

}