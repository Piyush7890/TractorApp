package com.wce.tractorapp.model;

import org.parceler.Parcel;

@Parcel
public class ChatPerson {
    String name;
    String email;
    String avatarUrl;
     String uid;
     String hisAvatarUrl;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ChatPerson() {
    }

    public ChatPerson(String name,
                      String email,
                      String avatarUrl,
                      String uid,
                      String hisAvatarUrl) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.uid = uid;
        this.hisAvatarUrl = hisAvatarUrl;
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

    public String getHisAvatarUrl() {
        return hisAvatarUrl;
    }

    public void setHisAvatarUrl(String hisAvatarUrl) {
        this.hisAvatarUrl = hisAvatarUrl;
    }
}
