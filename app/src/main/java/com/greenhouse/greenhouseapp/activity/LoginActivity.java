package com.greenhouse.greenhouseapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.greenhouse.greenhouseapp.R;
import com.greenhouse.greenhouseapp.controller.SessionManagement;
import com.greenhouse.greenhouseapp.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnRegister, btnLogIn;
    String email, password;
    RequestQueue requestQueue;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            email = extras.getString("email");
            password = extras.getString("password");
        }

        initUI();


        btnRegister.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }

    private void initUI(){


        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);


        btnRegister = findViewById(R.id.btnRegister);
        btnLogIn = findViewById(R.id.btnLogin);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {


        int id = v.getId();


        if (id == R.id.btnLogin) {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String encryptedPassword = null;

            try {
                encryptedPassword = encrypt(password);
            } catch (Exception e) {
                e.printStackTrace();
            }


            login(email, encryptedPassword);


        } else if (id == R.id.btnRegister) {

            sendToRegister();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String decrypt(String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(password);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
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

    private void checkSession() {

        //check if user is logged in
        //if user is logged in, move to mainActivity

        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();

        if(userID != -1) {
            //user is logged in, move to mainActivity
            sendToMenu(userID);
        } else {
            //do nothing
        }
    }

    public void login(String email, String password) {
        String URLDB = "http://192.168.0.3/greenhouseDB/login.php?email=" + email + "&password=" + password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URLDB,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String email, password, tries, name;
                        int id;
                        try{

                            id = response.getInt("id");
                            email = response.getString("email");
                            password = response.getString("password");
                            tries = response.getString("tries");
                            name = response.getString("name");


                            User user = new User(id, email, password);

                            if(Integer.parseInt(tries) > 0){
                                SessionManagement sessionManagement =  new SessionManagement(LoginActivity.this);
                                sessionManagement.saveSession(user);

                                Toast.makeText(LoginActivity.this, "Hello " + name + "!", Toast.LENGTH_SHORT).show();

                                sendToMenu(id);
                            } else {

                                Toast.makeText(LoginActivity.this, "User Blocked", Toast.LENGTH_SHORT).show();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this, "Authentication Error", Toast.LENGTH_SHORT).show();


                    }
                }
        );

        requestQueue.add(jsonObjectRequest);

    }

    public void sendToMenu(int id) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle sendData = new Bundle();
        sendData.putString("id", String.valueOf(id));
        i.putExtras(sendData);
        startActivity(i);
    }

    public void sendToRegister() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}