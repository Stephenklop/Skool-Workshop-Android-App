package com.example.skoolworkshop2.logic.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = MessagingService.class.getSimpleName();
    public static String messageData = "";

    @Override
    public void onNewToken(@NonNull String token) {

        Log.d(TAG, "Token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Room.databaseBuilder(this, LocalDb.class, "LocalDb");
        LocalDb.getDatabase(getApplication()).getNotificationDAO().insertNotification(new Notification("title", "description", "url", false));

//        messageData = remoteMessage.getData().toString();

        System.out.println("Notification received");
        System.out.println("---");
        System.out.println(remoteMessage.getCollapseKey());
        System.out.println(remoteMessage.getData());
        System.out.println(remoteMessage.getFrom());
        System.out.println(remoteMessage.getMessageId());
        System.out.println(remoteMessage.getMessageType());
        System.out.println(remoteMessage.getNotification());
        System.out.println(remoteMessage.getOriginalPriority());
        System.out.println(remoteMessage.getPriority());
        System.out.println(remoteMessage.getSenderId());
        System.out.println(remoteMessage.getSentTime());
        System.out.println(remoteMessage.getTo());
        System.out.println(remoteMessage.getTtl());
        System.out.println("---");

        Log.d(TAG, "Message: " + remoteMessage.toString());
    }

    public String getToken() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    Log.e(TAG, "Failed to get the token.");
                    return;
                }

                //get the token from task
                token[0] = task.getResult();

                Log.d(TAG, "Token : " + token[0]);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to get the token : " + e.getLocalizedMessage());
            }
        });
        return token[0];
    }


    public void handleNotificationData(Intent intent) {
        Bundle bundle = intent.getExtras();
        System.out.println(intent.getExtras());
        if(bundle != null) {
            if(bundle.containsKey("data1")) {
                Log.d(TAG, "Data1: " + bundle.getString("data1"));
            }
            if(bundle.containsKey("data2")) {
                Log.d(TAG, "Data2: " + bundle.getString("data2"));
            }
        }
    }

    /**
     * method to subscribe to topic
     *
     * @param topic to which subscribe
     */
    public void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(SplashScreenActivity.this, "Subscribed to " + topic, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Subscribed to " + topic);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(SplashScreenActivity.this, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed to subscribe");
            }
        });
    }

    /**
     * method to unsubscribe to topic
     *
     * @param topic to which unsubscribe
     */
    public void unsubscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void enableAnalytics(Context context) {
        FirebaseAnalytics.getInstance(context).setAnalyticsCollectionEnabled(true);
    }

    public void disableAnalytics(Context context) {
        FirebaseAnalytics.getInstance(context).setAnalyticsCollectionEnabled(false);
    }
}
