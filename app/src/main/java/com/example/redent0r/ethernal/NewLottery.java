package com.example.redent0r.ethernal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewLottery extends AppCompatActivity {

    private static final String serverUrl = "http://159.65.161.113:6000";
    private static final String serverUrlNewLottery = serverUrl + "/start";

    EditText etEntryAmount;
    EditText etMaxParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lottery);

        etEntryAmount = (EditText) findViewById(R.id.etEntryAmount);
        etMaxParticipants = (EditText)findViewById(R.id.etMaxParticipants);
    }

    private void newLottery() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", MainActivity.userId);
            jsonObject.put("entryAmount", Double.parseDouble(etEntryAmount.getText().toString()));
            jsonObject.put("maxParticipants", Integer.parseInt(etMaxParticipants.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest jsonRequest = new JsonObjectRequest(serverUrlNewLottery, jsonObject,
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
}
