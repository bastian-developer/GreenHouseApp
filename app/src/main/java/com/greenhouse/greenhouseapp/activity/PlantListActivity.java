package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.PlantListAdapter;
import com.greenhouse.greenhouseapp.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class PlantListActivity extends AppCompatActivity {

    //a List of type hero for holding list items
    List<Plant> plantList;

    //the listview
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        //initializing objects
        plantList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        //adding some values to our list
        plantList.add(new Plant(1, "Tomate", "Fruit", "Asia", R.drawable.image));
        plantList.add(new Plant(2, "Zanahoria", "Verdura", "Europa", R.drawable.image));
        plantList.add(new Plant(3, "Lechuga", "Arbol", "Africa", R.drawable.image));
        plantList.add(new Plant(4, "Platano", "Fruit", "Oceania", R.drawable.image));


        //creating the adapter
        PlantListAdapter adapter = new PlantListAdapter(this, R.layout.custom_list, plantList);

        //attaching adapter to the listview
        listView.setAdapter(adapter);
    }
}