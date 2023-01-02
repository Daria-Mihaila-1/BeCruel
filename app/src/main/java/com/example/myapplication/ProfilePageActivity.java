package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilePageActivity extends AppCompatActivity {

    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        usernameTextView = findViewById(R.id.usernameTextView);


        Intent intent = getIntent();
        String username =intent.getStringExtra("username");
        usernameTextView.setText(username);
    }
}