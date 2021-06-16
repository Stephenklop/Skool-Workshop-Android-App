package com.example.skoolworkshop2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;


public class WebViewActivity extends AppCompatActivity {

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }



        ImageButton backButton = findViewById(R.id.activity_web_btn_back);
        backButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));

        if(getIntent().hasExtra("url")){
            WebView wv = findViewById(R.id.activity_webview);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    TextView tvTitle = findViewById(R.id.activity_web_tv_page);
                    tvTitle.setText(wv.getTitle());


                    TextView tvUrl = findViewById(R.id.activity_web_tv_url);
                    tvUrl.setText(url);
                }
            });
            wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl(getIntent().getStringExtra("url"));


            View popup = findViewById(R.id.activity_web_popup);

            TextView refreshButton = findViewById(R.id.activity_web_menu_refresh);
            refreshButton.setOnClickListener(v -> {
                popup.setVisibility(View.GONE);
                wv.reload();
            });


            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setOnClickListener(v -> popup.setVisibility(View.GONE));

            wv.setOnTouchListener((v, event) -> {
                popup.setVisibility(View.GONE);
                return false;
            });

            TextView openInBrowser = findViewById(R.id.activity_web_menu_browser);
            TextView copyLink = findViewById(R.id.activity_web_menu_link);

            openInBrowser.setOnClickListener(v -> {
                popup.setVisibility(View.GONE);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(wv.getUrl())));
            });

            copyLink.setOnClickListener(v -> {
                setClipboard(getApplicationContext(), wv.getUrl());
                Toast copiedToast = new Toast(getApplicationContext());
                copiedToast.setText("Url is gekopieerd naar klembord");
                copiedToast.setDuration(Toast.LENGTH_LONG);
                copiedToast.show();
                popup.setVisibility(View.GONE);
            });

            ImageButton menuButton = findViewById(R.id.activity_web_btn_more);
            menuButton.setOnClickListener(v -> {
                if(popup.getVisibility() == View.GONE){
                    popup.setVisibility(View.VISIBLE);
                } else {
                    popup.setVisibility(View.GONE);
                }
            });
        }

    }

    private void setClipboard(Context context, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
    }
}
