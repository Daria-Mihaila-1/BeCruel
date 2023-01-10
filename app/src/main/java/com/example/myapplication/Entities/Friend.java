package com.example.myapplication.Entities;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class Friend {

    private DocumentReference friend;

    private DocumentReference owner;

    public Friend(DocumentReference friend, DocumentReference owner) {
        this.friend = friend;
        this.owner = owner;
    }

    public DocumentReference getFriend() {
        return friend;
    }

    public void setFriend(DocumentReference friend) {
        this.friend = friend;
    }

    public DocumentReference getOwner() {
        return owner;
    }

    public void setOwner(DocumentReference owner) {
        this.owner = owner;
    }

    public HashMap<String,Object> friendshipToMap(){
        HashMap<String,Object> friendship = new HashMap<>();

        friendship.put("owner",owner);
        friendship.put("friend",friend);
        return friendship;
    }
}
