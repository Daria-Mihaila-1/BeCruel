package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {
    private RecyclerView post_recyclerView;
    private ImageView profileIV;
    private ImageView logoutIV;

    private ArrayList<Post> posts;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        firebaseAuth = FirebaseAuth.getInstance();

        //NAVBAR INCA NEFUNCTIONAL


        profileIV = findViewById(R.id.profileIV);
        logoutIV = findViewById(R.id.logoutIV);
        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get profile user name from this page
                // go to profile activity
            }
        });
        
        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }});

//        navigationView = findViewById(R.id.navbar);
//        navigationView.setNavigationItemSelectedListener(item -> {
            //todo ViewProfile class
//            Intent intent = new Intent(FeedActivity.this, ViewProfile.class);
//            finish();
//            startActivity(intent);

//            return false;

//        });
        post_recyclerView = findViewById(R.id.post_recyclerView);

        posts = new ArrayList<>();
        int[] reactList = {1, 2,3};
        posts.add(new Post("Dan", R.drawable.batman, 0, 0,
            new Timestamp(2022, 10, 30, 22, 57, 30, 1),reactList));

        posts.add(new Post("Hannah", R.drawable.batman, 0, 0,
                        new Timestamp(2016, 2, 30, 22, 57, 30, 1),reactList));

        PostRecyclerAdapter postRecyclerAdapter = new PostRecyclerAdapter(posts);

        post_recyclerView.setAdapter(postRecyclerAdapter);
        post_recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void logout() {
        firebaseAuth.signOut();
        finish();
    }
}