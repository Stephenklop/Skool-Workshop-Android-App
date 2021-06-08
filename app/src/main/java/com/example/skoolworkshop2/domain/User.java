package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String token;
    private int id;
    private String username;
    private int points;
    //private String registrationDate; // Might be changed to Timestamp later (depends on whether that's useful)

    public User(String token, int id, String username, int points) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", points=" + points +
                '}';
    }
}