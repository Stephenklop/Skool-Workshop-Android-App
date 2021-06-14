package com.example.skoolworkshop2.ui.notifications;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("activity started");
        setContentView(R.layout.activity_notifications);

        LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllNotifications();

        RecyclerView rv = findViewById(R.id.activity_notifications_rv_notifications);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setAdapter(new NotificationsAdapter(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllNotifications(), getApplicationContext()));
        rv.setLayoutManager(linearLayoutManager);
    }
}
