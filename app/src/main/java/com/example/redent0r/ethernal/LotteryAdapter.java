package com.example.redent0r.ethernal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author redent0r
 *
 */

public class LotteryAdapter extends ArrayAdapter<Lottery> {

    private static final String TAG = LotteryAdapter.class.getSimpleName();

    NumberFormat currencyFormater = NumberFormat.getCurrencyInstance(); // for currency format
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");

    public LotteryAdapter(@NonNull Context context, int resource, @NonNull List<Lottery> objects) {
        super(context, resource, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_lottery, parent, false);

        TextView tvTransactionId = view.findViewById(R.id.tvTransactionId);

        TextView tvAmount = view.findViewById(R.id.tvAmount);

        TextView tvTime = view.findViewById(R.id.tvTime);

        TextView tvCompleted = view.findViewById(R.id.tvCompleted);

        final Lottery lottery = getItem(position);

        Log.d(TAG, "getView: id: "+ lottery.getId());

        tvTransactionId.setText(lottery.getId());

        tvAmount.setText(currencyFormater.format(lottery.getEntryAmount()));
        tvTime.setText(sdf.format(new Date(lottery.getTime())));

        tvCompleted.setText(lottery.getCompleted() + "");

        return view;
    }
}
