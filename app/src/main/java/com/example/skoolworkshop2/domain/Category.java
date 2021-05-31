package com.example.skoolworkshop2.domain;

public enum Category {
    BK("Beeldende Kunst"),
    MK("Muziek"),
    MA("Media"),
    DS("Dans"),
    ST("Sport"),
    TR("Theater");

    public final String label;

    private Category(String label) {
        this.label = label;
    }
}
