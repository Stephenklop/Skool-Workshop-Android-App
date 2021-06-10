package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NewsArticle implements Serializable {
    @ColumnInfo @PrimaryKey @NonNull
    private int id;

    @ColumnInfo
    private String url;

    @ColumnInfo
    private String imgUrl;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String date;

    public NewsArticle(int id, String url, String imgUrl, String name, String date) {
        this.id = id;
        this.url = url;
        this.imgUrl = imgUrl;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}


