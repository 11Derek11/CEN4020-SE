package com.example.redent0r.ethernal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author redent0r
 *
 */
public class LotteryFragment extends Fragment {

    private static final String TAG = LotteryFragment.class.getName();

    ListView lvHistory;
    LotteryAdapter lotteryAdapter;

    List<Lottery> lotteryList = new ArrayList<>();

    public static final String KEY_LOTTERY_ID = TAG + ".KEY_LOTTERY_ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lottery, container, false);

        lvHistory = (ListView) v.findViewById(R.id.lvHistory);

        //testLvHistory();

        lotteryAdapter = new LotteryAdapter(getContext(), R.layout.item_lottery, lotteryList);

        refresh();

        lvHistory.setAdapter(lotteryAdapter);

        ((FloatingActionButton)v.findViewById(R.id.floatingActionButton))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewLottery.class);
                startActivity(intent);
            }
        });

        setOnLotteryClick();

        return v;
    }

    private void setOnLotteryClick() {
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Lottery lottery = (Lottery) adapterView.getItemAtPosition(i);
                String lotteryAddress = lottery.getId();
                Intent intent = new Intent(getActivity().getApplicationContext(), LotterySummary.class);
                intent.putExtra(KEY_LOTTERY_ID, lotteryAddress);
                startActivity(intent);
            }
        });
    }

    private void joinLotteryConfirm() {
        /*
        final Lottery lottery = (Lottery) adapterView.getItemAtPosition(i);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity().getApplicationContext());
        alertDialogBuilder.setTitle("Join Lottery");
        alertDialogBuilder.setMessage("Do you want to join this lottery?");
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                joinLottery(lottery.getId());
            }
        });
        alertDialogBuilder.show();
        */
    }

    private void joinLottery(String lotteryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract", lotteryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest jsonRequest = new JsonObjectRequest(MainActivity.serverUrlPlayLottery, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("success")) {
                            Toast.makeText(getActivity().getApplicationContext(), "You have been added to this lottery", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "You couldn't join the lottery", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonRequest);
    }

    private void refresh() {

        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                MainActivity.serverUrlRefresh, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response);
                        lotteryList.clear();
                        try {
                            Iterator<String> iterator = response.keys();
                            while (iterator.hasNext()) {
                                String id = iterator.next();
                                JSONArray lottery = response.getJSONArray(id);

                                Double entryAmount = lottery.getDouble(2);
                                String ownerId = lottery.getString(0);
                                String lotteryId = lottery.getString(1);
                                Integer maxParticipants = lottery.getInt(3);
                                Long timestamp = Long.parseLong(lottery.getString(4))/10000;
                                //Boolean completed = lottery.getBoolean("completed");

                                Lottery l = new Lottery(lotteryId, entryAmount, ownerId, maxParticipants, false, timestamp);
                                lotteryList.add(l);
                                Log.d(TAG, "onResponse: lottery added: " + lotteryId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        lotteryAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonArrayRequest);
    }

    private void testLvHistory() {
        for(int i = 0; i < 10; ++i) {
            String id = new String(i + "");
            lotteryList.add(
                    new Lottery(id, 5D, "w", 1, false, 1520543308L));
        }
    }
}
