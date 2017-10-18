package com.example.victormotogna.para.model;

/**
 * Created by victormotogna on 10/16/17.
 */

public class User {
    // TODO: 10/16/17 implement ormlite for persistance

    private String name;
    private String password;
    private String phoneNumber;

    public User(String name, String password, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
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
}
