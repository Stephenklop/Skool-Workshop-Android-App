package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.PasswordValidator;
import com.google.android.material.textfield.TextInputLayout;

public class AccountActivity extends AppCompatActivity {

    private Button mLoginButton;
    private TextView mEmailEditText;
    private TextView mPasswordEditText;
    private TextInputLayout mPasswordLayout;
    private TextView mForgotPasswordTextView;
    private TextView mSignUpTextView;

    // Validators
    private PasswordValidator passwordValidator;
    private EmailValidator emailValidator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Button
        mLoginButton = findViewById(R.id.activity_login_btn_login);
        mLoginButton.setText("Login");

        // Validators
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();


        mEmailEditText = findViewById(R.id.activity_login_et_username);
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mEmailEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!emailValidator.isValidEmail(charSequence.toString())){
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emailValidator.isValidEmail(editable.toString())){
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    emailValidator.mIsValid = true;
                }
            }
        });
        mEmailEditText.setText("e.aygun1@student.avans.nl");

        mPasswordLayout = findViewById(R.id.activity_login_et_password);
        mPasswordEditText = findViewById(R.id.component_edittext_password);
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPasswordEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!passwordValidator.isValidPassword(charSequence.toString())){
                    mPasswordEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(passwordValidator.isValidPassword(editable.toString())){
                    mPasswordEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    passwordValidator.mIsValid = true;
                }
            }
        });
        mPasswordEditText.setText("61-Trabzon-61");


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

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIUserDAO apiUserDAO = new APIUserDAO();

                if(emailValidator.isValid() && passwordValidator.isValid()){
                    Thread loadUser = new Thread(() -> {
                        User user = apiUserDAO.signUserIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("USERNAME", user.getUsername());
                        startActivity(new Intent(getApplicationContext(), MyAccountActivity.class).putExtras(bundle));
                    });
                    try {
                        loadUser.join();
                        loadUser.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if(!emailValidator.isValid() && !passwordValidator.isValid()){
                        Toast.makeText(getApplicationContext(), "Email and password are incorrect given", Toast.LENGTH_SHORT).show();
                    } else if (!emailValidator.isValid() && passwordValidator.isValid()){
                        Toast.makeText(getApplicationContext(), "Email is incorrect given", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is incorrect given", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void enableLoadingIndicator() {
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                avd.start();
            }
        });
        avd.start();
        loadingIndicator.setVisibility(View.VISIBLE);
    }
}
