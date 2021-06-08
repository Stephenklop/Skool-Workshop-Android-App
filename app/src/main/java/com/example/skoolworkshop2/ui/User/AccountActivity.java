package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;

public class AccountActivity extends AppCompatActivity {

    private Button mLoginButton;
    private TextView mEmailEditText;
    private TextView mPasswordEditText;
    private TextView mForgotPasswordTextView;
    private TextView mSignUpTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Button
        mLoginButton = findViewById(R.id.activity_login_btn_login);
        mLoginButton.setText("Login");

        mSignUpTextView = findViewById(R.id.activity_login_txt_create_account);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mForgotPasswordTextView = findViewById(R.id.activity_login_forgot_password_txt);
        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPassIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(forgotPassIntent);
            }
        });
    }
}
