package com.example.redent0r.ethernal;

/**
 * Created by Derek on 3/8/2018.
 * All mistakes were introduced by Saul
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText etUserName, etPassword;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CookieHandler.setDefault(new CookieManager());

        Log.d(TAG, "onCreate: "+ CookieHandler.getDefault());

        btnLogin = (Button)findViewById(R.id.btnLogin);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUserName.getText().toString().equals("") &&
                        etPassword.getText().toString().equals("")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.KEY_USER_ID, "0x123456789");
                    startActivity(intent);
                    finish();
                }
                else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", etUserName.getText().toString());
                        jsonObject.put("password", etPassword.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonRequest jsonRequest = new JsonObjectRequest(MainActivity.serverUrlLogin, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                        if (response.has("success")) {
                                            try {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra(MainActivity.KEY_USER_ID, response.getString("success"));
                                                startActivity(intent);
                                                finish();
                                            }
                                            catch (JSONException e) {e.printStackTrace();}
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                                        }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", etUserName.getText().toString());
                    jsonObject.put("password", etPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonRequest jsonRequest = new JsonObjectRequest(MainActivity.serverUrlRegister, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                    if (response.has("success")) {
                                        Toast.makeText(getApplicationContext(), "You have been succesfully registered", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "There was an error registering you", Toast.LENGTH_LONG).show();
                                    }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
            }
        });
    }
}