package com.example.myapplication.Entities;

public class User {
    private String username;
    private String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public User(){}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }



}