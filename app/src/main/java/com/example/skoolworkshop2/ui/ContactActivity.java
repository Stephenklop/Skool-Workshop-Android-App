package com.example.skoolworkshop2.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.notifications.OldNotificationAdapter;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        ImageButton backButton = findViewById(R.id.activity_contact_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ConstraintLayout mAddressLayout = findViewById(R.id.activity_contact_cl_address);
        ConstraintLayout mTelLayout = findViewById(R.id.activity_contact_cl_tel);
        ConstraintLayout mEmailLayout = findViewById(R.id.activity_contact_cl_email);
        ConstraintLayout mInstagramLayout = findViewById(R.id.activity_contact_cl_insta);
        ConstraintLayout mFacebookLayout = findViewById(R.id.activity_contact_cl_fb);
        ConstraintLayout mLinkedinLayout = findViewById(R.id.activity_contact_cl_lnkd);
        ConstraintLayout mYoutubeLayout = findViewById(R.id.activity_contact_cl_yt);

        mAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://goo.gl/maps/V3iJQMF8fqoh2ovw9"));
            }
        });

        mTelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0850653923"));
                startActivity(callIntent);
            }
        });

        mEmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/html");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "info@skoolworkshop.nl");
                startActivity(emailIntent);
            }
        });

        mInstagramLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://www.instagram.com/skoolworkshop/"));
            }
        });

        mFacebookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://www.facebook.com/skoolworkshop"));
            }
        });

        mLinkedinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://www.linkedin.com/company/3733062/"));
            }
        });

        mYoutubeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://www.youtube.com/channel/UCkTSL4U0IEk-6eVC6RwkyOQ"));
            }
        });

    }
}
