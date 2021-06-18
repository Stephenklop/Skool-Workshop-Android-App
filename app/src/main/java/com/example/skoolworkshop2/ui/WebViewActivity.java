package com.example.skoolworkshop2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        enableLoadingIndicator();



        ImageButton backButton = findViewById(R.id.activity_web_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().hasExtra("url")){
            WebView wv = findViewById(R.id.activity_webview);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    disableLoadingIndicator();
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

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_web_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_web_img_loading_indicator);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                avd.start();
            }
        });
        loadingAlert.setAlpha(0);
        loadingAlert.setVisibility(View.VISIBLE);
        loadingAlert.animate().alpha(1).setDuration(200).start();
        avd.start();
    }

    private void disableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_web_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_web_img_loading_indicator);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }
}
