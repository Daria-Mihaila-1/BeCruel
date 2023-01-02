package com.example.myapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Entities.User;
import com.example.myapplication.ProfilePageActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class UserArrayAdapter extends ArrayAdapter<User> {
    private Context c;
    // constructor for our list view adapter.
    public UserArrayAdapter(@NonNull Context context, ArrayList<User> usersArrayList) {
        super(context, 0, usersArrayList);
        this.c = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.text_lv_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        User user = getItem(position);

        // initializing our UI components of list view item.
        TextView emailTV = listitemView.findViewById(R.id.idTVemail);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        emailTV.setText(user.getEmail());

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(view -> {
            Intent profilePageIntent = new Intent(c, ProfilePageActivity.class);
            profilePageIntent.putExtra("username", user.getUsername());
            c.startActivity(profilePageIntent);
        });
        return listitemView;
    }
}
