package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button yesBtn;
    Button noBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            SharedPreferences sharedpreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            int i = sharedpreferences.getInt("loggedIn", 0);
            if(i > 0){
                Intent loginIntent = new Intent(MainActivity.this, FeedActivity.class);
                startActivity(loginIntent);
            }
        }
        catch (Exception e){
            SharedPreferences sharedpreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("loggedIn", 0);
            editor.apply();
        }

        setContentView(R.layout.activity_main);

        // on below line we are initializing variables with ids.
        yesBtn = findViewById(R.id.buttonYes);

        noBtn = findViewById(R.id.buttonNo);
        noBtn.setOnClickListener(view -> {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });


    }

    }

