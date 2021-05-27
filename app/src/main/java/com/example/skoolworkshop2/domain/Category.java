package com.example.skoolworkshop2.domain;

public enum Category {
    BK("Beeldende Kunst"),
    MK("Muziek"),
    MA("Media"),
    DS("Dans");

    public final String label;

    private Category(String label) {
        this.label = label;
    }
}
