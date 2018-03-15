package com.example.redent0r.ethernal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author redent0r
 */

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

        TextView tvUserName = (TextView)view.findViewById(R.id.tvUserName);
        TextView tvPublicKey = (TextView)view.findViewById(R.id.tvAddress);

        final User user = getItem(position);

        String userName = user.getName();
        String address = user.getAddress();

        tvUserName.setText(userName);
        tvPublicKey .setText(address);

        return view;
    }
}
