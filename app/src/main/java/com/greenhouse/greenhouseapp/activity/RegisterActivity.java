package com.greenhouse.greenhouseapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etName, etPassword, etEmail, etPhoto, etStreet;
    Spinner spinnerCountry, spinnerState,spinnerCity;
    Button btnCreate, btnLogin;

    RequestQueue requestQueue, requestQueueSpin;

    ArrayList<String> countryList = new ArrayList<>();
    ArrayList<String> stateList = new ArrayList<>();
    ArrayList<String> cityList = new ArrayList<>();
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> cityAdapter;

    private static final String URLDB = "http://192.168.0.3/greenhousedb/saveuser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueueSpin = Volley.newRequestQueue(RegisterActivity.this);

        initUI();

        btnCreate.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    private void initUI() {

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhoto = findViewById(R.id.etPhoto);
        btnCreate = findViewById(R.id.btnCreateUser);
        btnLogin = findViewById(R.id.btnLogIn);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerCity = findViewById(R.id.spinnerCity);
        etStreet = findViewById(R.id.etStreet);

        String URL = "http://192.168.0.3/greenhousedb/populateCountries.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("countries");

                            for (int i = 0; i<jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String countryName = jsonObject.optString("name");
                                countryList.add(countryName);
                                countryAdapter = new ArrayAdapter<>(RegisterActivity.this,
                                        //Replaced android default spinner theme with a custom one
                                        R.layout.color_spinner_layout, countryList);
                                countryAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                                spinnerCountry.setAdapter((countryAdapter));
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

        requestQueueSpin.add(jsonObjectRequest);
        spinnerState.setOnItemSelectedListener(this);
        spinnerCountry.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnCreateUser) {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String photo = etPhoto.getText().toString().trim();
            String isBlocked = "false";
            String address = spinnerCountry.getSelectedItem().toString() + ", " +
                                spinnerState.getSelectedItem().toString() + ", " +
                                    spinnerCity.getSelectedItem().toString() + ", " +
                                        etStreet.getText().toString().trim();

            createUser(name, email, password, address, photo, isBlocked);

            sendToLogIn();

        } else if (id == R.id.btnLogIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }


    private void createUser(final String name, final String email, final String password, final String address,final String photo, final String isBlocked) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, "BKN", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String e = error.toString();

                        Toast.makeText(RegisterActivity.this, e, Toast.LENGTH_SHORT).show();
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

    public void sendToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.spinnerCountry) {
            stateList.clear();
            String selectedCountry = parent.getSelectedItem().toString();
            requestQueueSpin = Volley.newRequestQueue(RegisterActivity.this);
            String URL = "http://192.168.0.3/greenhousedb/populateStates.php?name=" + selectedCountry;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("states");

                                for (int i = 0; i<jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String stateName = jsonObject.optString("name");
                                    stateList.add(stateName);
                                    stateAdapter = new ArrayAdapter<>(RegisterActivity.this,
                                            //Replaced android default spinner theme with a custom one
                                            R.layout.color_spinner_layout, stateList);
                                    stateAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                                    spinnerState.setAdapter((stateAdapter));
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

            requestQueueSpin.add(jsonObjectRequest);

        }

        if(parent.getId() == R.id.spinnerState) {

            cityList.clear();
            String selectedState = parent.getSelectedItem().toString();
            requestQueueSpin = Volley.newRequestQueue(RegisterActivity.this);
            String URL = "http://192.168.0.3/greenhousedb/populateCities.php?name=" + selectedState;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("cities");

                                for (int i = 0; i<jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String cityName = jsonObject.optString("name");
                                    cityList.add(cityName);
                                    cityAdapter = new ArrayAdapter<>(RegisterActivity.this,
                                            //Replaced android default spinner theme with a custom one
                                            R.layout.color_spinner_layout, cityList);
                                    cityAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                                    spinnerCity.setAdapter((cityAdapter));
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

            requestQueueSpin.add(jsonObjectRequest);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}