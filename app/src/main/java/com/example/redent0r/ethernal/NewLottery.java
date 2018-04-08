package com.example.redent0r.ethernal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class NewLottery extends AppCompatActivity {

    EditText etEntryAmount;
    EditText etMaxParticipants;

    private static final String TAG = NewLottery.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lottery);

        etEntryAmount = (EditText) findViewById(R.id.etEntryAmount);
        etMaxParticipants = (EditText)findViewById(R.id.etMaxParticipants);
        ((Button)findViewById(R.id.btnConfirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLottery();
            }
        });
    }

    private void newLottery() {
        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put("userId", MainActivity.userId);
            jsonObject.put("bet", etEntryAmount.getText().toString());
            jsonObject.put("players", etMaxParticipants.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "newLottery: " + jsonObject);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, MainActivity.serverUrlStartLottery, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, "onResponse: "+response);
                        if (response.has("success")) {
                            String lotteryId = "";
                            Log.d(TAG, "onResponse: " + response);
                            try {
                                lotteryId = response.getString("success");
                                //Log.d(TAG, "onResponse: " + response);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(),
                                    "Your lottery: " + lotteryId + " has been succesfully created", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "There was an error with lottery creation", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse: json error response: " + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy((40 * 1000), DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
        Toast.makeText(getApplicationContext(), "Your lottery has been created succesfully", Toast.LENGTH_LONG).show();
    }
}
