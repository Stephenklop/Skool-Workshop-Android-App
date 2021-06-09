package com.example.skoolworkshop2.domain;

public class NewsArticle {
    private String url;
    private String imgUrl;
    private String name;

    public NewsArticle(String url, String imgUrl, String name) {
        this.url = url;
        this.imgUrl = imgUrl;
        this.name = name;
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

    @Override
    public String toString() {
        return "NewsArticle{" +
                "url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}


