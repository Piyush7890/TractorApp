package com.wce.tractorapp.model;


import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RenterData {
     String name;
     String email;
     String equipmentType;
     String description;
     String title;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    String contact;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid;



    String avatarUrl;
     boolean availabilityStatus;
     String date;
     int rent;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

     List<String> urls;
     String city;

    public RenterData(String name,
                      String email,
                      String title,
                      String equipmentType,
                      String description,
                      boolean availabilityStatus,
                      String date,
                      int rent,
                      List<String> urls,
                      String city,
                      String avatarUrl,
                      String uid,
                      String contact){
        this.name = name;
        this.email = email;
        this.title = title;
        this.equipmentType = equipmentType;
        this.description = description;
        this.availabilityStatus = availabilityStatus;
        this.date = date;
        this.rent = rent;
        this.urls = urls;
        this.city = city;
        this.avatarUrl = avatarUrl;
        this.uid = uid;
        this.contact = contact;
    }


    public RenterData() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
