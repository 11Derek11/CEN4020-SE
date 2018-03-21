package com.example.redent0r.ethernal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author redent0r
 *
 */

public class HomeFragment extends Fragment {

    private static HomeFragment instance = null;

    TextView tvAddress1;
    TextView tvAddress2;

    public TextView tvWallet1;
    public TextView tvWallet2;
    public TextView tvStatus;

    EditText etInput;

    GreetingClient greetingClient;

    public static HomeFragment getInstance() {
        if(instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tvAddress1 = (TextView)v.findViewById(R.id.tvAddress1);
        tvAddress2 = (TextView)v.findViewById(R.id.tvAddress2);

        tvWallet1 = (TextView)v.findViewById(R.id.tvWallet1);
        tvWallet2 = (TextView)v.findViewById(R.id.tvWallet2);

        tvStatus = (TextView)v.findViewById(R.id.tvStatus);

        etInput = (EditText)v.findViewById(R.id.etInput);

        ((Button)v.findViewById(R.id.btnBet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playFlip();
            }
        });

        ((Button)v.findViewById(R.id.btnConnect)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToServer();
            }
        });

        greetingClient = new GreetingClient(this);

        return v;
    }

    private void connectToServer() {
        greetingClient.start();
    }

    private void playFlip() {
        greetingClient.sendMessageToServer(etInput.getText().toString());
    }
}
