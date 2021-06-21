package com.example.skoolworkshop2.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Bank {
    @Ignore
    private Context context;

    @ColumnInfo @PrimaryKey @NonNull
    private String id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String logoUrl;

    public Bank(@NonNull String id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "context=" + context +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", svg='" + logoUrl + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    public String getName() {
        return name;
    }

}
