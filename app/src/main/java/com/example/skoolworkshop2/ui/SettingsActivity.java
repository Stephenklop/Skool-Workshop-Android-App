package com.example.skoolworkshop2.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.notifications.MessagingService;
import com.google.firebase.analytics.FirebaseAnalytics;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SettingsActivity extends AppCompatActivity {
    private ImageButton mBackBtn;
    private Switch mNotificationsSwitch;
    private Switch mAnalyticsSwitch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mBackBtn = findViewById(R.id.activity_settings_btn_back);
        mNotificationsSwitch = findViewById(R.id.activity_settings_tgl_notifications);
        mAnalyticsSwitch = findViewById(R.id.activity_settings_tgl_analytics);

        mBackBtn.setOnClickListener(v -> {
            finish();
        });

        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MessagingService messagingService = new MessagingService();

                if(isChecked) {
                    messagingService.unsubscribeToTopic("main");
                } else {
                    messagingService.subscribeToTopic("main");
                }
            }
        });

        mAnalyticsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MessagingService messagingService = new MessagingService();

                if(isChecked) {
                    messagingService.enableAnalytics(getApplicationContext());
                } else {
                    messagingService.disableAnalytics(getApplicationContext());
                }
            }
        });
    }
}
