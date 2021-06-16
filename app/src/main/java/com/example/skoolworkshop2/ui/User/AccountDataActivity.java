package com.example.skoolworkshop2.ui.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.SplashScreenActivity;

public class AccountDataActivity extends AppCompatActivity /*extends Activity*/ {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }
        // Successfully logged in
//        Toast.makeText(this, "You have successfully logged in as "+this.getIntent().getExtras().getString("USERNAME"), Toast.LENGTH_LONG).show();

    }
}