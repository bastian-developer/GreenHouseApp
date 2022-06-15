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
import com.greenhouse.greenhouseapp.activity.PlantActivity;
import com.greenhouse.greenhouseapp.activity.UserActivity;
import com.greenhouse.greenhouseapp.model.Plant;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//we need to extend the ArrayAdapter class as we are building an adapter
public class PlantListAdapter extends ArrayAdapter<Plant> {

    //the list values in the List of type hero
    List<Plant> plantList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    RequestQueue requestQueue;

    String userID;

    //constructor initializing the values
    public PlantListAdapter(Context context, int resource, List<Plant> plantList, String userID) {
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

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewName = view.findViewById(R.id.textViewName);
        Button buttonEdit = view.findViewById(R.id.buttonEdit);
        Button buttonDelete = view.findViewById(R.id.buttonDelete);

        //getting the hero of the specified position
        Plant plant = plantList.get(position);

        //adding values to the list item
        new GetImageFromUrl(imageView).execute(plant.get_image());
        textViewName.setText(plant.get_name());

        //adding a click listener to the button to remove item from the list
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
                removePlant(position);
                deletePlant(String.valueOf(plant.get_id()));
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendToEditPlant(String.valueOf(plant.get_id()), userID);

            }
        });

        //finally returning the view
        return view;
    }

    public void sendToEditPlant(String id, String userID) {

        Intent i = new Intent(context, EditPlantActivity.class);
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

    //this method will remove the item from the list
    private void removePlant(final int position) {
        //Creating an alert dialog to confirm the deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                plantList.remove(position);

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePlant(final String idPlant) {

        requestQueue = Volley.newRequestQueue(context);

        String URLDB = "http://192.168.0.3/greenhousedb/deletePlant.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idPlant);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
