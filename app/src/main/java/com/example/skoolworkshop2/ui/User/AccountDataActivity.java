package com.example.skoolworkshop2.ui.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.PointsLayoutTestActivity;

public class AccountDataActivity extends AppCompatActivity /*extends Activity*/ {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);

        String firstName;
        String lastName;
        String displayName;
        String emailAddress;
        ImageButton mBackButton;

//        //firstName
//        TextView firstNameTextView = (EditText) findViewById(R.id.activity_account_data_et_first_name);
//        firstName = firstNameTextView.getText().toString();

        //button update account information
        View updateAccountInfo = findViewById(R.id.activity_account_data_btn_confirm);
        updateAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO names and email needs to be changed here.
            }
        });

        //password
        View updatePassword = findViewById(R.id.activity_account_data_btn_password);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });

        //TODO: back button
        mBackButton = findViewById(R.id.activity_account_data_btn_back);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MyAccountActivity.class);
                startActivity(backIntent);
            }
        });
        //TODO: if click on button firstname, lastname displayname and email are changed.
    }
}