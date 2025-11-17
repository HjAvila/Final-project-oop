package com.example.finaoop.models;

import java.io.Serializable;

public class User implements Serializable {

    // Add a serialVersionUID for file saving
    private static final long serialVersionUID = 1L;

    private String fullName;
    private String email;
    private String password;

    // NEW FIELDS
    private String address;
    private String phoneNumber;

    // Update the constructor
    public User(String fullName, String email, String password, String address, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters for all fields
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}