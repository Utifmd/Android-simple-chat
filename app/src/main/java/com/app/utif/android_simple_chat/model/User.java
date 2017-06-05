package com.app.utif.android_simple_chat.model;

import com.google.firebase.database.Exclude;

/**
 * Created by utif on 6/5/17.
 */

public class User {
    private Long time;
    private String uid;
    private String sender;
    private String photo;
    private String recipient;

    public User(){}
    public User(String uid, String sender, String photo, Long time) {
        this.uid = uid;
        this.sender = sender;
        this.photo = photo;
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Exclude
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
