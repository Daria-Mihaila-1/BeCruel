package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {
    private RecyclerView post_recyclerView;

    private NavigationView navigationView;

    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //NAVBAR INCA NEFUNCTIONAL
        navigationView = findViewById(R.id.navbar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

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
}