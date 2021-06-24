package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reservation {
    @PrimaryKey @NonNull @ColumnInfo
    private int id;
    @ColumnInfo
    private String status;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private int costumerId;
    @ColumnInfo
    private String type;

    public Reservation(int id, String status, String date, int costumerId, String type) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.costumerId = costumerId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
