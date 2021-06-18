package com.example.skoolworkshop2.domain;

import android.graphics.drawable.Drawable;

public class Country {
    private Drawable flag;
    private String name;

    public Country(Drawable flag, String name) {
        this.flag = flag;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Drawable getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return name;
    }
}
