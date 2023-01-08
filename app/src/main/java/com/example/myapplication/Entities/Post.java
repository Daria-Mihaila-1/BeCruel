package com.example.myapplication.Entities;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.text.DateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Post {

    private byte[] postImage;
    private GeoPoint location;
    private Date timestamp;
    private String crimeDescription;
    private Timestamp firebaseTimestamp;


    public Post(byte[] postImage, GeoPoint location, Timestamp timestamp, String crimeDescription) {

        this.postImage = postImage;
        this.location = location;
        this.firebaseTimestamp = timestamp;
        this.crimeDescription = crimeDescription;

    }

    public Post(byte[] postImage, GeoPoint location, Date timestamp, String crimeDescription) {

        this.postImage = postImage;
        this.location = location;
        this.timestamp = timestamp;
        this.crimeDescription = crimeDescription;
    }
    public Post(GeoPoint location, Date timestamp, String crimeDescription) {

        this.location = location;
        this.timestamp = timestamp;
        this.crimeDescription = crimeDescription;
    }
    public byte[] getPostImage() {
        return postImage;
    }

    public Timestamp getFirebaseTimestamp() {
        return firebaseTimestamp;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    public double getLocation_latitude() {
        return location.getLatitude();
    }


    public double getLocation_longitude() {
        return location.getLongitude();
    }

    public String getTimestamp() {
        Date myTime = new Date(timestamp.getSeconds()*1000);
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(myTime);
    }

    public GeoPoint getLocation() {
        return this.location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getCrimeDescription() {
        return crimeDescription;
    }

    public void setCrimeDescription(String crimeDescription) {
        this.crimeDescription = crimeDescription;
    }

    public HashMap<String, Object> postToHashmap(String user){
        Map<String, Object> post = new HashMap<>();
        String base64String = Base64.getEncoder().encodeToString(getPostImage());
        post.put("crime", getCrimeDescription());
        post.put("imgByteArray", base64String);
        post.put("location", getLocation());
        post.put("timestamp", getFirebaseTimestamp());
        post.put("user", "Users/" + user);

        return (HashMap<String, Object>) post;

    }

}