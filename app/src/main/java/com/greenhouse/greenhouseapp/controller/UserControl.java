package com.greenhouse.greenhouseapp.controller;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserControl{

    RequestQueue requestQueue;

    private static final String URLSAVE = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/saveUser.php";
    private static final String URLBLOCK = "http://"+ Connection.GLOBAL_IP + "/greenhousedb/blockUser.php";

    public void createUser(final String name, final String email, final String password, final String address,final String photo, final String isBlocked, Context context ) {

        requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLSAVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String e = error.toString();
                    }
                }
        ){
            //Hashmap URL ENCODE
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("address", address);
                params.put("photo", photo);
                params.put("isBlocked", isBlocked);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void blockUser(final String email, final String isBlocked, Context context ) {

        requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLBLOCK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String e = error.toString();

                    }
                }
        ){
            //Hashmap URL ENCODE
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("isBlocked", isBlocked);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}
