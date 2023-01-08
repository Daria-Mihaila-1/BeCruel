package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Entities.Post;
import com.example.myapplication.Utils.PostRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfilePageActivity extends AppCompatActivity {

    private TextView emailTextView;
    private RecyclerView post_recyclerView;
    private TextView friendsCountTV;
    private TextView postCountTV;
    private ImageView logoutIV;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DocumentReference docRef;
    private static ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        emailTextView = findViewById(R.id.emailTextView);
        logoutIV = findViewById(R.id.logoutIV);
        postCountTV = findViewById(R.id.postCountTV);
        posts.clear();
        Intent intent = getIntent();
        String email =intent.getStringExtra("email");
        docRef = db.collection("Users").document(email);
        emailTextView.setText(email);

        friendsCountTV = findViewById(R.id.friendsCountTV);


        post_recyclerView = findViewById(R.id.post_recyclerView);



        getPosts();
        setFriendsCount();
        System.out.println(posts.size());

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }});

    }

    private void getPosts() {

        final CollectionReference dbPosts = db.collection("Posts");

        dbPosts
                .whereEqualTo("user", docRef)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                GeoPoint location = (GeoPoint) d.get("location");
                                Timestamp timestamp = (Timestamp) d.get("timestamp");
                                Date myTime = new Date(timestamp.getSeconds()*1000);
                                String crimeDescription = (String) d.get("crime");
                                String base64Encoded = (String) d.get("imgByteArray");
                                byte[] file = Base64.decode(base64Encoded, Base64.DEFAULT);

                                Post newPost = new Post(file,location, myTime, crimeDescription);
                                posts.add(newPost);
                            }

                            //setting the recycler view's data
                            PostRecyclerAdapter postRecyclerAdapter = new PostRecyclerAdapter(posts, ProfilePageActivity.this);

                            post_recyclerView.setAdapter(postRecyclerAdapter);
                            post_recyclerView.setLayoutManager(new LinearLayoutManager(ProfilePageActivity.this));
                            postCountTV.setText(String.valueOf(posts.size()));
                        }
                        else {
                            System.out.println("no posts available");
                        }
                    }
                });
    }

    public void setFriendsCount() {
        final CollectionReference dbFriends = db.collection("Friends");

        dbFriends
                .whereEqualTo("owner", docRef)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            friendsCountTV.setText(String.valueOf(list.size()));
                        }
                    }
                });

    }

    private void goBack() {
        //we are clearing the STATIC list
        finish();
    }

}