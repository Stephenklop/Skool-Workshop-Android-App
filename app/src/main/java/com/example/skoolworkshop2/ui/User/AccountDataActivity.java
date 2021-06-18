package com.example.skoolworkshop2.ui.User;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.example.skoolworkshop2.ui.WebViewActivity;

public class AccountDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);


        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }
        // Successfully logged in
//        Toast.makeText(this, "You have successfully logged in as "+this.getIntent().getExtras().getString("USERNAME"), Toast.LENGTH_LONG).show();

        ImageButton mBackButton;



        String firstName = LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getFirstName();
        String lastName = LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getLastName();
        String emailUser = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getEmail();
        String displayName = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getUsername();

        System.out.println("Firstname: " + firstName);
        System.out.println("Lastname: " + lastName);
        System.out.println("EmailUser:" + emailUser);
        System.out.println("DisplayName: " + displayName);

        //firstName
        EditText firstNameText = (EditText) findViewById(R.id.activity_account_data_et_first_name);
        firstNameText.setText(firstName);
        //lastName
        EditText lastNameText = (EditText) findViewById(R.id.activity_account_data_et_last_name);
        lastNameText.setText(lastName);
        //displayName
        EditText displayNameText = (EditText) findViewById(R.id.activity_account_data_et_display_name);
        displayNameText.setText(displayName);
        //email
        EditText emailText = (EditText) findViewById(R.id.activity_account_data_et_email);
        emailText.setText(emailUser);

        EmailValidator emailValidator = new EmailValidator();



        //button update account information
        View updateAccountInfo = findViewById(R.id.activity_account_data_btn_confirm);
        updateAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        APIDAOFactory apidaoFactory = new APIDAOFactory();
//                        apidaoFactory.getUserDAO().updateUser("bbuijsen@gmail.com", "bbuijsen", "BartTest", "Buijsen");
                    }
                }).start();


                firstNameText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        firstNameText.setText(LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getFirstName());
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                lastNameText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        lastNameText.setText(LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getLastName());
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                displayNameText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        displayNameText.setText(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getUsername());
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                emailText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        emailText.setText(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getEmail());
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        //update user info
        View updateInfo = findViewById(R.id.activity_account_data_btn_confirm);
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIUserDAO apiUserDAO = new APIUserDAO();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        apiUserDAO.updateUser(emailText.getText().toString(), displayNameText.getText().toString(), firstNameText.getText().toString(), lastNameText.getText().toString());
                    }
                }).start();
            }
        });

        //password
        View updatePassword = findViewById(R.id.activity_account_data_btn_password);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mBackButton = findViewById(R.id.activity_account_data_btn_back);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MyAccountActivity.class);
                startActivity(backIntent);
            }
        });
    }
}