package com.wce.tractorapp.model;

public class Sort {
    int sortType;
    String location;
    String type;

    public Sort(int sortType, String location, String type) {
        this.sortType = sortType;
        this.location = location;
        this.type = type;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
