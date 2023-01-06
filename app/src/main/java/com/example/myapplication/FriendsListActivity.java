package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.Entities.User;
import com.example.myapplication.Utils.UserArrayAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;
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
import java.util.List;


public class FriendsListActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView logoutIV;
    private ImageView profileIV;
    private ListView friendslistView;
    private ActivityMainBinding binding;

    private DocumentReference docRef ;
    private static ArrayList<User> users = new ArrayList<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private UserArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String emailFromLogin = sh.getString("email", "");

        friendslistView = findViewById(R.id.friendsListView);
        logoutIV = findViewById(R.id.logoutIV);
        profileIV = findViewById(R.id.profileIV);

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }});

        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent  = new Intent(getApplicationContext(), ProfilePageActivity.class);
                startActivity(profileIntent);
            }});



        docRef = db.collection("Users").document(emailFromLogin);

        getFriends();

        // after passing this array list to our adapter
        // class we are setting our adapter to our list view.
        friendslistView.setAdapter(adapter);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.addFriend:
                        startActivity(new Intent(getApplicationContext(), AddFriendActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.addPost:
                        startActivity(new Intent(getApplicationContext(), AddPostActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

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
                        }
                    }
                });
        // after that we are passing our array list to our adapter class.
        adapter = new UserArrayAdapter(FriendsListActivity.this, users);

        }

    private void logout() {
        firebaseAuth.signOut();
        //we are clearing the STATIC list
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        sh.edit().remove("email").commit();
        users.clear();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}