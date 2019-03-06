package com.wce.tractorapp.model;

public class SignUpData {
    private String name;
    private String password;
    private String email;
    private String contactNo;
    private String aadharNo;
    private String city;
    private String state;
    private String aadharUrl;
    private String address;
    private String avatarUrl;

    public SignUpData() {
    }

    public SignUpData(String name, String password, String email, String contactNo, String aadharNo, String city, String state, String aadharUrl, String address) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.contactNo = contactNo;
        this.aadharNo = aadharNo;
        this.city = city;
        this.state = state;
        this.aadharUrl = aadharUrl;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAadharUrl() {
        return aadharUrl;
    }

    public void setAadharUrl(String aadharUrl) {
        this.aadharUrl = aadharUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
