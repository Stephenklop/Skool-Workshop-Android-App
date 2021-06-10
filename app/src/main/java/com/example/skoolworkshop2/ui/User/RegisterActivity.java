package com.example.skoolworkshop2.ui.User;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.PasswordValidator;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private TextInputLayout mPasswordLayout;
    private EditText mPasswordEditText;
    private Button mSubmitButton;
    private TextView mLoginTextView;
    private final String LOG_TAG = getClass().getSimpleName();

    // Validators
    private PasswordValidator passwordValidator;
    private EmailValidator emailValidator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Validators
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();

        mEmailEditText = findViewById(R.id.activity_register_et_email);
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
        // Password
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

        // Submit
        mSubmitButton = findViewById(R.id.activity_register_btn_register);
        mSubmitButton.setText("Registreren");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIUserDAO apiUserDAO = new APIUserDAO();

                if(emailValidator.isValid() && passwordValidator.isValid()){
                    Thread loadUser = new Thread(() -> {
                        String[] parts = mEmailEditText.getText().toString().split("@");
                        String username = parts[0];
                        apiUserDAO.registerUser(username, mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());

                        Intent loginIntent = new Intent(getApplicationContext(), AccountActivity.class);
                        startActivity(loginIntent);


                    });
                    try {
                        loadUser.join();
                        loadUser.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Go to login activity
        mLoginTextView = findViewById(R.id.activity_register_txt_log_in);
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_register_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_register_img_loading_indicator);
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
        LinearLayout loadingAlert = findViewById(R.id.activity_register_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_register_img_loading_indicator);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }
}
