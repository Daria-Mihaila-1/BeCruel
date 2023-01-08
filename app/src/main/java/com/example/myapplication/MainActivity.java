package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button yesBtn;
    Button noBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String emailFromLogin = sh.getString("email", "");


        if(!emailFromLogin.equalsIgnoreCase("")){
            Intent finedListIntend = new Intent(MainActivity.this, FriendsListActivity.class);
            startActivity(finedListIntend);
        }

        setContentView(R.layout.activity_main);

        // on below line we are initializing variables with ids.
        yesBtn = findViewById(R.id.buttonYes);

        noBtn = findViewById(R.id.buttonNo);
        noBtn.setOnClickListener(view -> {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        yesBtn.setOnClickListener(view -> {
            finishAndRemoveTask();
        });

    }
    }

