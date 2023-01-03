package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilePageActivity extends AppCompatActivity {

    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        emailTextView = findViewById(R.id.emailTextView);


        Intent intent = getIntent();
        String username =intent.getStringExtra("email");
        emailTextView.setText(username);
    }
}