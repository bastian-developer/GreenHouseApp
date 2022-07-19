package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
        plantList.add(new Plant(1,1, "Lechugòn", "Fruit", "Asia", "http://192.168.0.3/greenhousedb/user_uploads/cabbage.png"));

        //Fill list with user plants
        searchPlants();

        //creating the adapter
        PlantListAdapter adapter = new PlantListAdapter(PlantListActivity.this, R.layout.custom_list, plantList, userID);
        //attaching adapter to the listview
        listView.setAdapter(adapter);
    }


    public void searchPlants() {
        requestQueue = Volley.newRequestQueue(PlantListActivity.this);
        String URL = "http://192.168.0.3/greenhousedb/populateUserPlants.php?userId=" + userID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("plants");

                            //Remove hardcoded tomato
                            //plantList.remove(0);

                            for (int i = 0; i<jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id, userId, name, type, origin, photos;


                                id = jsonObject.optString("id");
                                userId = jsonObject.optString("userId");
                                name = jsonObject.optString("name");
                                type = jsonObject.optString("type");
                                origin = jsonObject.optString("origin");
                                photos = jsonObject.optString("photos");

                                //Toast.makeText(PlantListActivity.this, userID, Toast.LENGTH_SHORT).show();


                                plantList.add(new Plant(Integer.parseInt(id),Integer.parseInt(userId), name, type, origin, photos));



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
}