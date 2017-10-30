package com.example.victormotogna.para.model;

import java.io.Serializable;

/**
 * Created by victormotogna on 10/16/17.
 */

public class User implements Serializable {
    // TODO: 10/16/17 implement ormlite for persistance

    private String name;
    private String password;
    private String phoneNumber;
    private double totalExpense;
    private String photoLocation;

    public User(String name, String password, String email, String photoLocation) {
        this.name = name;
        this.password = password;
        this.phoneNumber = email;
        this.photoLocation = photoLocation;
        this.totalExpense = 0.0;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
