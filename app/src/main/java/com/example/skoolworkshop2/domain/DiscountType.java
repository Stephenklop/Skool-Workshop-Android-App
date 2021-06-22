package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public enum DiscountType {
    PROCENTKORTING, VASTEKORTING, PRODUCTKORTING, POINTS;

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return super.toString();
    }
}
