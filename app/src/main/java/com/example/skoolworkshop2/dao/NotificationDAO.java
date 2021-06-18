package com.example.skoolworkshop2.dao;

import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;

import java.util.List;

public interface NotificationDAO {
    List<Notification> getNotificationsForUser(int id);
    List<Notification> getNotificationsForTopic(String topic);
}
