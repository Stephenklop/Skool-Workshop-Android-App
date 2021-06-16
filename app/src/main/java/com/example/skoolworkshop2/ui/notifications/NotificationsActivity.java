package com.example.skoolworkshop2.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.SplashScreenActivity;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("activity started");
        setContentView(R.layout.activity_notifications);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        loadNotifications();

        ImageButton backButton = findViewById(R.id.activity_notifications_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    public void loadNotifications(){
        RecyclerView rv = findViewById(R.id.activity_notifications_rv_notifications);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setAdapter(new NotificationsAdapter(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllNewNotifications(), getApplication(), new NotificationsAdapter.ClickListener() {
            @Override
            public void onClick() {
                loadNotifications();
            }
        }));
        rv.setLayoutManager(linearLayoutManager);

        RecyclerView oldRv = findViewById(R.id.activity_old_notifications_rv_notifications);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        oldRv.setAdapter(new OldNotificationAdapter(LocalDb.getDatabase(getApplication()).getNotificationDAO().getAllOldNotifications(), getApplicationContext()));
        oldRv.setLayoutManager(linearLayoutManager1);
    }
}
