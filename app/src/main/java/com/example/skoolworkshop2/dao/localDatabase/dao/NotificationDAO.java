package com.example.skoolworkshop2.dao.localDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Insert
    void insertNotification(Notification notification);

    @Query("SELECT * FROM NOTIFICATION")
    List<Notification> getAllNotifications();

    @Query("SELECT * FROM Notification WHERE id = :id")
    Notification getNotificationById(int id);

    @Query("DELETE FROM Notification WHERE id = :id")
    void deleteNotificationById(int id);
}
