package com.example.skoolworkshop2.ui.User;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.CountryValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.HouseNumberValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.NameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.PlaceValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.StreetnameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.UsernameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorBE;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorNL;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.example.skoolworkshop2.ui.WebViewActivity;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopBookingActivity;


public class AccountDataActivity extends AppCompatActivity {
    private String LOG_TAG = getClass().getSimpleName();
    //validations
    private NameValidator nameValidator = new NameValidator();
    private UsernameValidator usernameValidator = new UsernameValidator();

    private EmailValidator emailValidator = new EmailValidator();
    //Edit Text
    private EditText emailText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText displayNameText;
    private ImageButton mBackButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_data);
        nameValidator.mIsValid = true;
        emailValidator.mIsValid = true;
        usernameValidator.mIsValid = true;


        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }
        // Successfully logged in
//        Toast.makeText(this, "You have successfully logged in as "+this.getIntent().getExtras().getString("USERNAME"), Toast.LENGTH_LONG).show();





        String firstName = LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getFirstName();
        String lastName = LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getLastName();
        String emailUser = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getEmail();
        String displayName = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getUsername();

        System.out.println("Firstname: " + firstName);
        System.out.println("Lastname: " + lastName);
        System.out.println("EmailUser:" + emailUser);
        System.out.println("DisplayName: " + displayName);

        //firstName
        firstNameText = (EditText) findViewById(R.id.activity_account_data_et_first_name);
        firstNameText.setText(firstName);
        //lastName
        lastNameText = (EditText) findViewById(R.id.activity_account_data_et_last_name);
        lastNameText.setText(lastName);
        //displayName
        displayNameText = (EditText) findViewById(R.id.activity_account_data_et_display_name);
        displayNameText.setText(displayName);
        //email
        emailText = (EditText) findViewById(R.id.activity_account_data_et_email);
        emailText.setText(emailUser);




        // FIXME: DE HELFT VAN DE ACTIVITY STAAT IN DE ONCLICK LISTENER VAN DE UPDATE BUTTON
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
            }
        });

                firstNameText.setText(LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getFirstName());

                firstNameText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!firstNameText.equals("")){
                            if (nameValidator.isValidName(s.toString())){
                                firstNameText.setBackgroundResource(R.drawable.edittext_confirmed);
                                nameValidator.mIsValid = true;

                            } else{
                                Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                                firstNameText.setBackgroundResource(R.drawable.edittext_error);
                                nameValidator.mIsValid = false;
                            }

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                firstNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            if(nameValidator.isValid()) {
                                firstNameText.setBackgroundResource(R.drawable.edittext_default);
                            }
                        } else{
                            firstNameText.setBackgroundResource(R.drawable.edittext_focused);
                        }
                    }
                });
                 lastNameText.setText(LocalDb.getDatabase(getApplication()).getCustomerDAO().getCustomer().getLastName());
                lastNameText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!lastNameText.equals("")){
                            if (nameValidator.isValidName(s.toString())){
                                lastNameText.setBackgroundResource(R.drawable.edittext_confirmed);
                                nameValidator.mIsValid = true;

                            } else{
                                Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                                lastNameText.setBackgroundResource(R.drawable.edittext_error);
                                nameValidator.mIsValid = false;
                            }

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                lastNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            if(nameValidator.isValid()) {
                                lastNameText.setBackgroundResource(R.drawable.edittext_default);
                            }
                        } else{
                            lastNameText.setBackgroundResource(R.drawable.edittext_focused);
                        }
                    }
                });
                displayNameText.setText(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getUsername());

                displayNameText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!displayNameText.equals("")){
                            if (usernameValidator.isValidUsername(s.toString())){
                                displayNameText.setBackgroundResource(R.drawable.edittext_confirmed);
                                usernameValidator.mIsValid = true;

                            } else{
                                Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                                displayNameText.setBackgroundResource(R.drawable.edittext_error);
                                usernameValidator.mIsValid = false;
                            }

                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
        displayNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(usernameValidator.isValid()) {
                        displayNameText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    displayNameText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });
            emailText.setText(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getEmail());


            emailText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!emailText.equals("")) {
                            if (emailValidator.isValidEmail(s.toString())) {
                                emailText.setBackgroundResource(R.drawable.edittext_confirmed);
                                emailValidator.mIsValid = true;

                            } else {
                                Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                                emailText.setBackgroundResource(R.drawable.edittext_error);
                                emailValidator.mIsValid = false;
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            if(emailValidator.isValid()) {
                                emailText.setBackgroundResource(R.drawable.edittext_default);
                            }
                        } else{
                            emailText.setBackgroundResource(R.drawable.edittext_focused);
                        }
                    }
                });




        //update user info
        Button updateInfo = findViewById(R.id.activity_account_data_btn_confirm);
        updateInfo.setText("Veranderingen Opslaan");
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableLoadingIndicator();
                APIUserDAO apiUserDAO = new APIUserDAO();
                if(usernameValidator.isValid() && nameValidator.isValid() && emailValidator.isValid()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                            apiUserDAO.updateUser(emailText.getText().toString(), displayNameText.getText().toString(), firstNameText.getText().toString(), lastNameText.getText().toString());
                            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class));

                        }

                }).start();
                } else{
                    disableLoadingIndicator();
                    String header = "Updaten van user gefaald.";
                    String content = "Een of meer validatie(s) is fout.";
                    new RoundedDialog(AccountDataActivity.this, header, content);

                }
            }
        });

        //password
        Button updatePassword = findViewById(R.id.activity_account_data_btn_password);
        updatePassword.setText("Verander Wachtwoord");
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://skoolworkshop.nl/account/wachtwoord-vergeten/"));
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

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_account_data_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_account_data_img_loading_indicator);
        View backGround = findViewById(R.id.activity_account_data_loading_background);
        backGround.setVisibility(View.VISIBLE);
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
        LinearLayout loadingAlert = findViewById(R.id.activity_account_data_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_account_data_img_loading_indicator);
        View backGround = findViewById(R.id.activity_account_data_loading_background);
        backGround.setVisibility(View.GONE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }
}