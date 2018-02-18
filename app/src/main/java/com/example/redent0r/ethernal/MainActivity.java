package com.example.redent0r.ethernal;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        setUpBottomNav(bottomNavigationView);

        // initial fragment transaction. default = home
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.home_container, HomeFragment.getInstance()).commit();
    }

    private void setUpBottomNav(BottomNavigationView bmv) {
        bmv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragmentManager.beginTransaction().replace(R.id.home_container, HomeFragment.getInstance()).commit();
                        return true;
                    case R.id.action_search:
                        fragmentManager.beginTransaction().replace(R.id.home_container, SearchFragment.getInstance()).commit();
                        return true;
                    case R.id.action_history:
                        fragmentManager.beginTransaction().replace(R.id.home_container, HistoryFragment.getInstance()).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

}
