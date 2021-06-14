package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @ColumnInfo @PrimaryKey @NonNull
    private int id;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private int points;
    //private String registrationDate; // Might be changed to Timestamp later (depends on whether that's useful)


    public User(int id, String email, String username, int points) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
                "id=" + id +
                ", username='" + username + '\'' +
                ", points=" + points +
                '}';
    }
}