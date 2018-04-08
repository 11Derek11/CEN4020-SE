package com.example.redent0r.ethernal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LotterySummary extends AppCompatActivity {

    TextView tvWinnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_summary);

        tvWinnerId = (TextView) findViewById(R.id.tvWinnerId);

        String lotteryId = getIntent().getStringExtra(LotteryFragment.KEY_LOTTERY_ID);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract", lotteryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest jsonRequest = new JsonObjectRequest(MainActivity.serverUrlGetUserContracts, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("winnerId")) {
                            try {
                                String winnerId = response.getString("winnerId");
                                tvWinnerId.setText(winnerId);
                            }
                            catch (JSONException e) {e.printStackTrace();}
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
