package com.example.skoolworkshop2.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.SettingsEntity;
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

        if(LocalDb.getDatabase(getApplication()).getSettingsDAO().getSettings() == null){
            LocalDb.getDatabase(getApplication()).getSettingsDAO().insertSettings(new SettingsEntity(1, true, true));
        }

        mBackBtn.setOnClickListener(v -> {
            finish();
        });

        boolean analyticsEnabled = LocalDb.getDatabase(getApplication()).getSettingsDAO().getSettings().isAnalytics();
        boolean notificationsEnabled = LocalDb.getDatabase(getApplication()).getSettingsDAO().getSettings().isNotifications();

        mNotificationsSwitch.setChecked(notificationsEnabled);
        mAnalyticsSwitch.setChecked(analyticsEnabled);

        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MessagingService messagingService = new MessagingService();

                if(isChecked) {
                    messagingService.unsubscribeToTopic("main");
                    LocalDb.getDatabase(getApplication()).getSettingsDAO().setNotificationsEnabled();
                } else {
                    messagingService.subscribeToTopic("main");
                    LocalDb.getDatabase(getApplication()).getSettingsDAO().setNotificationsDisabled();
                }
            }
        });

        mAnalyticsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MessagingService messagingService = new MessagingService();

                if(isChecked) {
                    messagingService.enableAnalytics(getApplicationContext());
                    LocalDb.getDatabase(getApplication()).getSettingsDAO().setAnalyticsEnabled();
                } else {
                    messagingService.disableAnalytics(getApplicationContext());
                    LocalDb.getDatabase(getApplication()).getSettingsDAO().setAnalyticsDisabled();
                }
            }
        });
    }
}
