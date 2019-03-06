package com.wce.tractorapp.model;

import org.parceler.Parcel;

@Parcel
public class ChatPerson {
    String name;
    String email;
    String avatarUrl;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ChatPerson(String name, String email, String avatarUrl, String uid) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
