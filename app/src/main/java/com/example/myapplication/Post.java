package com.example.myapplication;

import java.sql.Time;
import java.sql.Timestamp;

public class Post {
    private String username;
    private int postImage;
    private int location_latitude;
    private int location_longitude;
    private Timestamp timestamp;
    private int[] react;

    public Post(String username, int postImage, int location_latitude, int location_longitude, Timestamp timestamp, int[] react) {
        this.username = username;
        this.postImage = postImage;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
        this.timestamp = timestamp;
        this.react = react;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public int getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(int location_latitude) {
        this.location_latitude = location_latitude;
    }

    public int getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(int location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getTimestamp() {
        return timestamp.toString();
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int[] getReact() {
        return react;
    }

    public void setReact(int[] react) {
        this.react = react;
    }
}
