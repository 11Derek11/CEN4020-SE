package com.example.redent0r.ethernal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
public class LotteryFragment extends Fragment {

    private static final String TAG = LotteryFragment.class.getName();

    ListView lvHistory;
    LotteryAdapter lotteryAdapter;

    List<Lottery> lotteryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lottery, container, false);

        lvHistory = (ListView) v.findViewById(R.id.lvHistory);

        testLvHistory();

        lotteryAdapter = new LotteryAdapter(getContext(), R.layout.item_lottery, lotteryList);

        lvHistory.setAdapter(lotteryAdapter);

        ((FloatingActionButton)v.findViewById(R.id.floatingActionButton))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewLottery.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void testLvHistory() {
        for(int i = 0; i < 10; ++i) {
            String id = new String(i + "");
            lotteryList.add(
                    new Lottery(id, 5D, "w", false, 1520543308L));
        }
    }
}
