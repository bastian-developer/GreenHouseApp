package com.greenhouse.greenhouseapp.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.activity.EditPlantActivity;
import com.greenhouse.greenhouseapp.activity.MainActivity;
import com.greenhouse.greenhouseapp.activity.PlantInfoActivity;
import com.greenhouse.greenhouseapp.model.Plant;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalPlantListAdapter extends ArrayAdapter<Plant> {

    //the list values in the List of type hero
    List<Plant> plantList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    RequestQueue requestQueue;

    String userID;


    //constructor initializing the values
    public GlobalPlantListAdapter(Context context, int resource, List<Plant> plantList, String userID) {
        super(context, resource, plantList);
        this.context = context;
        this.resource = resource;
        this.plantList = plantList;
        this.userID = userID;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Get the view of the xml for our list item with layoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewName = view.findViewById(R.id.textViewName);
        Button buttonInfo = view.findViewById(R.id.buttonInfo);
        Button buttonCopy = view.findViewById(R.id.buttonCopy);

        //getting the plant of the specified position
        Plant plant = plantList.get(position);

        //adding values to the list item
        new GlobalPlantListAdapter.GetImageFromUrl(imageView).execute(plant.get_image());
        textViewName.setText(plant.get_name());

        //adding a click listener to the button to remove item from the list
        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                copyPlant(String.valueOf(plant.get_id()));

            }
        });

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendToPlantInfo(String.valueOf(plant.get_id()), userID);

            }
        });

        //finally returning the view
        return view;
    }

    public void sendToPlantInfo(String id, String userID) {

        Intent i = new Intent(context, PlantInfoActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle sendData = new Bundle();
        sendData.putString("plantId", String.valueOf(id));
        sendData.putString("id", userID);
        i.putExtras(sendData);
        context.startActivity(i);
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        ImageView imgV;

        public GetImageFromUrl(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {

            String urldisplay = url[0];

            Bitmap bitmapIcon = null;

            try {
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmapIcon = BitmapFactory.decodeStream(srt);
            } catch (Exception e) {

            }

            return bitmapIcon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgV.setImageBitmap(bitmap);
        }
    }


    private void copyPlant(final String idPlant) {

        requestQueue = Volley.newRequestQueue(context);

        String URLDB = "http://10.42.16.192/greenhousedb/copyPlant.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, "Added to My Plants", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idPlant);
                params.put("userId", userID);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
