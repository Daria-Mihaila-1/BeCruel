package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entities.Friend;
import com.example.myapplication.Entities.User;
import com.example.myapplication.Utils.UserArrayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddFriendActivity extends AppCompatActivity {

    private ImageButton search;
    private TextView friendName;
    private ListView friendlistView;
    private static ArrayList<User> users = new ArrayList<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserArrayAdapter adapter;
    private DocumentReference documentReference;
    private final CollectionReference usersReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String emailFromLogin = sh.getString("email", "");

        setContentView(R.layout.activity_add_friend);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.addFriend);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.addFriend:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.addPost:
                        startActivity(new Intent(getApplicationContext(), AddPostActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        documentReference = db.collection("Users").document(emailFromLogin);
        // create the fields we need to search for the stuff
        search = (ImageButton) findViewById(R.id.imageButton2);
        friendName = findViewById(R.id.searchName);
        friendlistView = findViewById(R.id.friends);
        search();
        addFirend();
    }

    public void search() {


        search.setOnClickListener(new View.OnClickListener() {
            final CollectionReference dbFriends = db.collection("Users");

            @Override
            public void onClick(View view) {
                users = new ArrayList<>();
                String input = friendName.getText().toString();
                dbFriends
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                    for (DocumentSnapshot snapshot : list
                                    ) {
                                        if (snapshot != null) {
                                            if (snapshot.get("username").toString().contains(input)) {

                                                boolean found = false;
                                                if (snapshot != null) {
                                                    User user = new User(snapshot.get("username").toString(), snapshot.get("email").toString());

                                                    for (User currentUser : users) {
                                                        if (Objects.equals(user.getUsername(), currentUser.getUsername())) {
                                                            found = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!found) {
                                                        users.add(user);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    adapter = new UserArrayAdapter(AddFriendActivity.this, users);

                                    friendlistView.setAdapter(adapter);
                                }
                            }
                        });
            }
        });
    }

    public void addFirend() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String emailFromLogin = sh.getString("email", "");
        ArrayList<User> currentFriends = getFriends();
        friendlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User clickedUser = (User) friendlistView.getItemAtPosition(i);
                final CollectionReference dbFriends = db.collection("Friends");
                DocumentReference friendReferece = db.collection("Users").document(clickedUser.getEmail());
                if (currentFriends.contains(clickedUser)) {

                    Toast.makeText(getApplicationContext(), "You already follow " + clickedUser.getUsername(), Toast.LENGTH_LONG).show();
                } else {
                    Friend friend = new Friend(friendReferece,documentReference);
                    UUID randomUUID = UUID.randomUUID();
                    db.collection("Friends").document(randomUUID.toString().replaceAll("_", ""))
                            .set(friend.postToHashmap(friend.getFriend(), friend.getOwner()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("POST", "Post submitted");
                                    Context context = getApplicationContext();
                                    Toast.makeText(context, "Friend added succesfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context,MainPageActivity.class));
                                }
                            });
                    UUID random2UUID = UUID.randomUUID();
                    db.collection("Friends").document(random2UUID.toString().replaceAll("_", ""))
                            .set(friend.postToHashmap(friend.getOwner(), friend.getFriend()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("POST", "Post submitted");
                                    Context context = getApplicationContext();
                                    startActivity(new Intent(context,MainPageActivity.class));
                                }
                            });
                }
            }
        });
    }



    protected ArrayList<User> getFriends() {
        final CollectionReference dbFriends = db.collection("Friends");
        ArrayList<User> myFriends = new ArrayList<>();
        dbFriends
                .whereEqualTo("owner", documentReference)
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
                                                    User user = new User(documentSnapshot.get("username").toString(), documentSnapshot.get("email").toString());
                                                    boolean found = false;
                                                    for (User u : users) {
                                                        if (u.getEmail().equals(user.getEmail())) {
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
                        }
                    }
                });
        return users;
    }
}