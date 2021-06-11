package com.example.skoolworkshop2.dao.localDatabase.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InfoEntity {

    @ColumnInfo
    public String name;

    @ColumnInfo @PrimaryKey @NonNull
    public String email;

    @ColumnInfo
    public String token;

    @ColumnInfo
    public String password;

    @ColumnInfo int points;

    @ColumnInfo
    int userId;

    public InfoEntity(String name, String email, String token, String password, int points, int userId) {
        this.name = name;
        this.email = email;
        this.token = token;
        this.password = password;
        this.points = points;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
