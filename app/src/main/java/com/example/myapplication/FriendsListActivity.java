package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.Entities.User;

import com.example.myapplication.Utils.UserArrayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FriendsListActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView logoutIV;
    private ImageView profileIV;
    private ListView friendslistView;

    private DocumentReference docRef ;
    private static ArrayList<User> users = new ArrayList<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private UserArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Intent intentFromLogin = getIntent();
        String emailFromLogin =intentFromLogin.getStringExtra("email");

        friendslistView = findViewById(R.id.friendsListView);
        logoutIV = findViewById(R.id.logoutIV);
        profileIV = findViewById(R.id.profileIV);

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }});




        docRef = db.collection("Users").document(emailFromLogin);

        getFriends();

        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent  = new Intent(getApplicationContext(), ProfilePageActivity.class);
                profileIntent.putExtra("email", emailFromLogin);
                System.out.println(users.size());
                profileIntent.putExtra("friendsCount", users.size());
                startActivity(profileIntent);
            }});


    }


    protected void getFriends() {
        final CollectionReference dbFriends = db.collection("Friends");

        dbFriends
                .whereEqualTo("owner", docRef)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                DocumentReference documentReference = (DocumentReference) d.get("friend");
                                documentReference
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    User user = new User(documentSnapshot.get("username").toString(),documentSnapshot.get("email").toString());
                                                    boolean found = false;
                                                    for (User u : users) {
                                                        if (u.getEmail().equals(user.getEmail())){
                                                            found = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!found) {
                                                        users.add(user);
                                                    }
                                                }
                                            }
                                        });
                            }
                            // after that we are passing our array list to our adapter class.
                            adapter = new UserArrayAdapter(FriendsListActivity.this, users);
                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            friendslistView.setAdapter(adapter);
                        }
                    }
                });


        }

    private void logout() {
        firebaseAuth.signOut();
        //we are clearing the STATIC list
        users.clear();
        finish();
    }



}


