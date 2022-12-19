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
    private ListView friendslistView;
    private ArrayList<User> friends = new ArrayList<>();
    private DocumentReference docRef ;
    private static ArrayList<User> users = new ArrayList<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        friendslistView = findViewById(R.id.friendsListView);
        logoutIV = findViewById(R.id.logoutIV);

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }});



        Intent intentFromLogin = getIntent();
        String emailFromLogin =intentFromLogin.getStringExtra("email");
        docRef = db.collection("Users").document(emailFromLogin);
        getFriends();
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
                            //we are clearing the STATIC list

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
                                                    User user = new User(documentSnapshot.get("email").toString(), documentSnapshot.get("username").toString());
                                                    if (!users.contains(user)) users.add(user);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
        // after that we are passing our array list to our adapter class.
        UserArrayAdapter adapter = new UserArrayAdapter(FriendsListActivity.this, users);
        // after passing this array list to our adapter
        // class we are setting our adapter to our list view.
        friendslistView.setAdapter(adapter);
    }

    private void logout() {
        firebaseAuth.signOut();
        finish();
    }

}


