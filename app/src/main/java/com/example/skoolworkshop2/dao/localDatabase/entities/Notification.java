package com.example.skoolworkshop2.dao.localDatabase.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notification {
    @PrimaryKey @NonNull @ColumnInfo
    int id;

    @ColumnInfo
    String title;

    @ColumnInfo
    String description;

    @ColumnInfo
    String url;

    @ColumnInfo
    boolean status;

    public Notification(int id,String title, String description, String url, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
