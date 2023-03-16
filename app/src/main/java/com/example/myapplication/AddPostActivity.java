package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myapplication.Entities.Post;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.Timestamp;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class AddPostActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    int MY_PERMISSION_ACCESS_COARSE_LOCATION = 100;

    GeoPoint currentLocation = new GeoPoint(0.0, 0.0);

    Button BSelectImage;

    Button PostItButton;

    ImageView IVPreviewImage;

    int TAKE_PICTURE = 200;

    Bitmap image_as_bitmap = null;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //image code
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getCurrentLocation(104, null)
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("gps", "Location permission granted");
                                Log.d("gps_location", location.toString());
                                Log.d("gps_location_longitude", Double.toString(location.getLongitude()));
                                Log.d("gps_location_latitude", Double.toString(location.getLatitude()));
                                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                            }
                            else{
                                Log.d("gps", "Location permission granted, but not accessible");
                            }
                        }
                    });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.addPost);

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
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.addPost:
                        return true;
                }
                return false;
            }
        });

        PostItButton = findViewById(R.id.PostItButton);

        PostItButton.setOnClickListener(view -> onSubmitPost());


    }

    void onSubmitPost(){

        EditText edit = (EditText) findViewById(R.id.editTextTextPersonName3);
        String crimeDescriptionString = (String) edit.getText().toString();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = null;
        if(image_as_bitmap != null) {
            image_as_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }

        Log.d("myPostArray", byteArray.toString());
        Post myPost = new Post(byteArray, currentLocation, Timestamp.now(), crimeDescriptionString);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String emailFromLogin = sh.getString("email", "");

        UUID randomUUID = UUID.randomUUID();
        db = FirebaseFirestore.getInstance();
        db.collection("Posts").document(randomUUID.toString().replaceAll("_", ""))
                .set(myPost.postToHashmap(emailFromLogin))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("POST", "Post submitted");
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Post added succesfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MainPageActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("POST", "Post not submitted");
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Error upon adding post!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MainPageActivity.class));
                    }
                });
    }

    void imageChooser() {

        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // TAKE_PICTURE constant
            if (requestCode == TAKE_PICTURE) {
                //transform Intent data into bitmap
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                image_as_bitmap = photo;
                try {
                    // update the preview image in the layout
                        IVPreviewImage.setImageBitmap(photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


