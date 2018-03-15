package com.example.redent0r.ethernal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author redent0r
 *
 */

public class SearchFragment extends Fragment {

    ListView lvUsers;
    UserAdapter userAdapter;
    List<User> userList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        lvUsers = (ListView)v.findViewById(R.id.lvUsers);

        testLvUsers();

        userAdapter = new UserAdapter(getActivity().getApplicationContext(), R.layout.item_user, userList);

        lvUsers.setAdapter(userAdapter);

        return v;
    }

    private void testLvUsers() {
        for(int i = 0; i < 10; ++i) {
            userList.add(
                    new User("John Doe", "0xC2D7CF95645D33006175B78989035C7c9061d3F9"));
        }
    }
}
