package com.example.skoolworkshop2.ui.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.PasswordValidator;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }



        View root = findViewById(R.id.activity_register);
        MenuController menuController = new MenuController(root);
        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(4).setChecked(true);

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
                enableLoadingIndicator();
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
                } else {
                    disableLoadingIndicator();
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


        String text = "Al een account? Log in";
        Spannable textSpannable = new SpannableString(text);
        textSpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)), 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mLoginTextView.setText(textSpannable);


        TextView tvPrivacy = findViewById(R.id.textView3);
        String privacyText = "Je persoonlijke gegevens worden gebruikt om je ervaring op deze site te ondersteunen, om toegang tot je account te beheren en voor andere doeleinden zoals omschreven in ons privacybeleid.";
        Spannable privacyTextSpannable = new SpannableString(privacyText);
        privacyTextSpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)), 173, 186, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrivacy.setText(privacyTextSpannable);
        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://skoolworkshop.nl/privacybeleid/")));
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
