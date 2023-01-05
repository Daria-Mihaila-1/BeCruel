package com.example.myapplication;

import com.example.myapplication.Entities.User;
import com.google.firebase.firestore.GeoPoint;

import java.text.DateFormat;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Post {

    private int postImage;
    private GeoPoint location;
    private Date timestamp;
    private String crimeDescription;


    public Post(int postImage, GeoPoint location, Date timestamp, String crimeDescription) {

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
    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public double getLocation_latitude() {
        return location.getLatitude();
    }


    public double getLocation_longitude() {
        return location.getLongitude();
    }

    public String getTimestamp() {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(timestamp);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

}
