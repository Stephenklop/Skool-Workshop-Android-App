package com.example.skoolworkshop2.domain;

import android.content.Context;

import java.net.URL;

public class Quiz {
    private Context context;

    private String title;
    private String url;
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
