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

import java.util.List;


public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private static final String TAG = Transaction.class.getSimpleName();

    public TransactionAdapter(@NonNull Context context, int resource, @NonNull List<Transaction> objects) {
        super(context, resource, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);

        TextView tvTransactionId = view.findViewById(R.id.tvTransactionId);

        final Transaction transaction = getItem(position);

        Log.d(TAG, "getView: id: "+ transaction.getId());

        tvTransactionId.setText(transaction.getId());

        return view;
    }
}
