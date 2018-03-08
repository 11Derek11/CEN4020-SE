package com.example.redent0r.ethernal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author redent0r
 *
 */

public class HomeFragment extends Fragment {

    private static HomeFragment instance = null;

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
