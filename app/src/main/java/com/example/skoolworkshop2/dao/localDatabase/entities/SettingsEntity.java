package com.example.skoolworkshop2.dao.localDatabase.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SettingsEntity {
    @ColumnInfo @PrimaryKey
    private int id;

    @ColumnInfo
    private boolean notifications;

    @ColumnInfo
    private boolean analytics;

    public SettingsEntity(int id, boolean notifications, boolean analytics) {
        this.id = id;
        this.notifications = notifications;
        this.analytics = analytics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public boolean isAnalytics() {
        return analytics;
    }

    public void setAnalytics(boolean analytics) {
        this.analytics = analytics;
    }
}
