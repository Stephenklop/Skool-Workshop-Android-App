package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.dao.localDatabase.entities.SettingsEntity;

import java.util.Set;

@Dao
public interface SettingsEntityDAO {
    @Query("SELECT * FROM SettingsEntity")
    SettingsEntity getSettings();

    @Query("DELETE FROM SettingsEntity")
    void deleteSettings();

    @Insert
    void insertSettings(SettingsEntity settings);

    @Query("UPDATE SettingsEntity SET Notifications = 1")
    void setNotificationsEnabled();

    @Query("UPDATE SettingsEntity SET Notifications = 0")
    void setNotificationsDisabled();

    @Query("UPDATE SettingsEntity SET Analytics = 1")
    void setAnalyticsEnabled();

    @Query("UPDATE SettingsEntity SET Analytics = 0")
    void setAnalyticsDisabled();

}
