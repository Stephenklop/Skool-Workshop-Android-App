package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
}
