package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;

public class AccountDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);

        ImageButton mBackButton;




        //button update account information
        View updateAccountInfo = findViewById(R.id.activity_account_data_btn_confirm);
        updateAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO names and email needs to be changed here.
                //voornaam, achternaam en email -- bij customer
                //username en email -- bij user

                //firstName
                EditText firstNameText = (EditText) findViewById(R.id.activity_account_data_et_first_name);
                String firstName = firstNameText.getText().toString();
                //lastName
                EditText lastNameText = (EditText) findViewById(R.id.activity_account_data_et_last_name);
                String lastName = lastNameText.getText().toString();
                //email
                EditText emailText = (EditText) findViewById(R.id.activity_account_data_et_email);
                String email = emailText.getText().toString();
                //username
                EditText displayNameText = (EditText) findViewById(R.id.activity_account_data_et_display_name);
                String displayName = displayNameText.getText().toString();

                //print out firstname, lastname, displayname en email
                System.out.println("firstName: " + firstName);
                System.out.println("lastName: " + lastName);
                System.out.println("displayName: " + displayName);
                System.out.println("email: " + email);
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