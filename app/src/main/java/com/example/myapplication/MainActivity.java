package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_main);

        // on below line we are initializing variables with ids.
        yesBtn = findViewById(R.id.buttonYes);

        // on below line we are adding click listener for our button
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are finishing activity.
                MainActivity.this.finish();

                // on below line we are exiting our activity
                System.exit(0);
            }
        });
    }

}