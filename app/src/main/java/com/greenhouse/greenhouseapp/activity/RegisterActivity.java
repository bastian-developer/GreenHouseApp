package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.RequiresApi;
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
import com.greenhouse.greenhouseapp.controller.UserControl;
import com.greenhouse.greenhouseapp.model.User;

import android.content.Intent;
import android.os.Build;
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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etName, etPassword, etPassword2, etEmail, etPhoto, etStreet;
    Spinner spinnerCountry, spinnerState,spinnerCity;
    Button btnCreate, btnLogin;
    String AES = "AES";

    RequestQueue requestQueue, requestQueueSpin;

    ArrayList<String> countryList = new ArrayList<>();
    ArrayList<String> stateList = new ArrayList<>();
    ArrayList<String> cityList = new ArrayList<>();

    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> cityAdapter;

    UserControl userControl = new UserControl();


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
        etPassword2 = findViewById(R.id.etPassword2);
        etEmail = findViewById(R.id.etEmail);
        //etPhoto = findViewById(R.id.etPhoto);
        btnCreate = findViewById(R.id.btnCreateUser);
        btnLogin = findViewById(R.id.btnLogIn);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerCity = findViewById(R.id.spinnerCity);
        etStreet = findViewById(R.id.etStreet);

        String URL = "http://10.42.16.192/greenhousedb/populateCountries.php";
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnCreateUser) {

            createUser();
            sendToLogIn();
            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.btnLogIn) {

            sendToLogIn();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createUser() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        /*
        String encryptedPassword = "";
        try {
            encryptedPassword = encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

         */

        //Hardcode
        String photo = "http://10.42.16.192/greenhousedb/user_uploads/image.jpg";
        String isBlocked = "false";

        String address = spinnerCountry.getSelectedItem().toString() + ", " +
                spinnerState.getSelectedItem().toString() + ", " +
                spinnerCity.getSelectedItem().toString() + ", " +
                etStreet.getText().toString().trim();

        userControl.createUser(name, email, password, address, photo, isBlocked, RegisterActivity.this);

    }

    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String encrypt(String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        //StandardCharsets.UTF_8
        byte[] encVal = c.doFinal(password.getBytes());

        //!!!!!
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);

        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //"UTF-8"
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


     */

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
            String URL = "http://10.42.16.192/greenhousedb/populateStates.php?name=" + selectedCountry;
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
            String URL = "http://10.42.16.192/greenhousedb/populateCities.php?name=" + selectedState;
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