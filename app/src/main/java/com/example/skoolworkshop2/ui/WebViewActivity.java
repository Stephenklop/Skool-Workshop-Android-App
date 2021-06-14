package com.example.skoolworkshop2.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        View root = findViewById(R.id.activity_web_view);
        MenuController mc = new MenuController(root);
        BottomNavigationView menu = findViewById(R.id.activity_menu_buttons);
        menu.getMenu().setGroupCheckable(0, false, true);

        if(getIntent().hasExtra("url")){
            WebView wv = (WebView) findViewById(R.id.activity_webview);
            wv.setWebViewClient(new WebViewClient());
            wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl(getIntent().getStringExtra("url"));
        }

    }
}
