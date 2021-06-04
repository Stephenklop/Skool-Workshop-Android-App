package com.example.skoolworkshop2.domain;

import java.io.Serializable;

public class Image implements Serializable {
    private int id;
    private String src;
    private String thumbnail;
    private String srcset;
    private String name;
    private String alt;

    public Image(int id, String src, String thumbnail, String srcset, String name, String alt) {
        this.id = id;
        this.src = src;
        this.thumbnail = thumbnail;
        this.srcset = srcset;
        this.name = name;
        this.alt = alt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSrcset() {
        return srcset;
    }

    public void setSrcset(String srcset) {
        this.srcset = srcset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
