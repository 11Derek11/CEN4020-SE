package com.example.redent0r.ethernal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewLottery extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lottery);

        getSupportActionBar().setTitle("New Lottery");
    }
}
