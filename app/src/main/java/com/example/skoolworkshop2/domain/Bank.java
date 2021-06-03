package com.example.skoolworkshop2.domain;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Bank {
    private Context context;

    private String id;
    private String name;
    private String svg;


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
                ", svg='" + svg + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSvg() {
        return svg;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }


    public Bank(String id, String name, String svgUrl) {
        this.id = id;
        this.name = name;
        this.svg = svgUrl;
    }

    public String getName() {
        return name;
    }

}
