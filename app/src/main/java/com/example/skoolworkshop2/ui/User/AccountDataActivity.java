package com.example.skoolworkshop2.ui.User;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;

public class AccountDataActivity extends AppCompatActivity /*extends Activity*/ {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);
        // Successfully logged in
//        Toast.makeText(this, "You have successfully logged in as "+this.getIntent().getExtras().getString("USERNAME"), Toast.LENGTH_LONG).show();

    }
}