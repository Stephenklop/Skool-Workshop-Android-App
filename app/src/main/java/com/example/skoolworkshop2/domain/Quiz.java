package com.example.skoolworkshop2.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.net.URL;

@Entity
public class Quiz {
    @Ignore
    private Context context;

    @ColumnInfo @PrimaryKey @NonNull
    private String title;

    @ColumnInfo
    private String url;

    @ColumnInfo
    private boolean status;

    public Quiz(String title, String url, boolean status) {
        this.title = title;
        this.url = url;
        this.status = status;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public boolean isStatus() {
        return status;
    }
}
