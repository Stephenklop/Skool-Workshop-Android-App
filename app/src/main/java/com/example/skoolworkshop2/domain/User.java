package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private Customer customer;
    private String username;
    private String password;
    private String registrationDate; // Might be changed to Timestamp later (depends on whether that's useful)

    public User(int id, Customer customer, String username, String password, String registrationDate) {
        this.id = id;
        this.customer = customer;
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}