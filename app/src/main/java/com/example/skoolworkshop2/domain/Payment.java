package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Payment {
    @ColumnInfo
    private String resource;

    @ColumnInfo @PrimaryKey @NonNull
    private String id;

    @ColumnInfo
    private String mode;

    @ColumnInfo
    private String createdAt;

    @ColumnInfo
    private String amount;

    @ColumnInfo
    private String currency;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private String method;

    @ColumnInfo
    private String status;

    @ColumnInfo
    private String expiresAt;

    @ColumnInfo
    private String locale;

    @ColumnInfo
    private String redirectUrl;

    @ColumnInfo
    private String checkoutUrl;

    public Payment(String resource, String id, String mode, String createdAt, String amount, String currency, String description, String method, String status, String expiresAt, String locale, String redirectUrl, String checkoutUrl) {
        this.resource = resource;
        this.id = id;
        this.mode = mode;
        this.createdAt = createdAt;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.method = method;
        this.status = status;
        this.expiresAt = expiresAt;
        this.locale = locale;
        this.redirectUrl = redirectUrl;
        this.checkoutUrl = checkoutUrl;
    }

    public String getResource() {
        return resource;
    }

    public String getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getLocale() {
        return locale;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "resource='" + resource + '\'' +
                ", id='" + id + '\'' +
                ", mode='" + mode + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                ", locale='" + locale + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", checkoutUrl='" + checkoutUrl + '\'' +
                '}';
    }
}
