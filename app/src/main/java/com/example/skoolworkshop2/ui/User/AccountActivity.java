package com.example.skoolworkshop2.ui.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIUserDAO;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.PasswordValidator;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.example.skoolworkshop2.ui.WebViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }


        mSignUpTextView = findViewById(R.id.activity_login_txt_create_account);

        String text = "Nog geen account? Maak er een";
        Spannable textSpannable = new SpannableString(text);
        textSpannable.setSpan(new ForegroundColorSpan(getColor(R.color.main_orange)), 18, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignUpTextView.setText(textSpannable);


        UserManager um = new UserManager(getApplication());
//        if(um.hasInfo()){
//            Bundle bundle = new Bundle();
//            bundle.putString("USERNAME", um.getInfo().getUsername());
//            startActivity(new Intent(getApplicationContext(), MyAccountActivity.class).putExtras(bundle));
//        }
        // Button
        mLoginButton = findViewById(R.id.activity_login_btn_login);
        mLoginButton.setText("Login");

        View root = findViewById(R.id.activity_login);
        MenuController menuController = new MenuController(root);
        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(4).setChecked(true);

        // Validators
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();


        mEmailEditText = findViewById(R.id.activity_login_et_username);
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mEmailEditText.equals("")) {
                        emailValidator.mIsValid = true;
                    } else{
                    emailValidator.mIsValid = false;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(emailValidator.isValid()) {
                        mEmailEditText.setBackgroundResource(R.drawable.edittext_default);

                    }
                } else{
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_focused);
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
                if(!mPasswordEditText.equals("")) {
                        passwordValidator.mIsValid = true;


                } else {
                    passwordValidator.mIsValid = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(passwordValidator.isValid()) {
                        mPasswordEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mPasswordEditText.setBackgroundResource(R.drawable.edittext_focused);
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
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class).putExtra("url", "https://skoolworkshop.nl/account/wachtwoord-vergeten/"));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                enableLoadingIndicator();
                DAOFactory apidaoFactory = new APIDAOFactory();
                mLoginButton.setEnabled(false);
                APIUserDAO apiUserDAO = new APIUserDAO();

                if(emailValidator.isValid() && passwordValidator.isValid()){
                    Thread loadUser = new Thread(() -> {
                        User user = apiUserDAO.signUserIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
                        Bundle bundle = new Bundle();
//                        bundle.putString("USERNAME", user.getUsername());
                        um.insertInfo(user);
                        LocalDb.getDatabase(getApplication()).getCustomerDAO().addCustomer(apiUserDAO.getLastCustomer());

                        setToken();

                        ArrayList<Notification> personalNotifications = (ArrayList<Notification>) apidaoFactory.getNotificationDAO().getNotificationsForUser(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getId());
                        for (Notification notification : personalNotifications) {
                            try{
                                LocalDb.getDatabase(getApplication()).getNotificationDAO().insertNotification(notification);
                            }catch (Exception e){
                                System.out.println("Notification bestaat al");
                            }
                        }

                        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(AccountActivity.this);
                        Bundle loginEvent = new Bundle();
                        loginEvent.putString("login_event_id", "login_event_id");
                        mFirebaseAnalytics.logEvent("login_event", loginEvent);

                        startActivity(new Intent(getApplicationContext(), MyAccountActivity.class).putExtras(bundle));
                    });
                    try {
                        loadUser.join();
                        loadUser.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    disableLoadingIndicator();
                    mLoginButton.setEnabled(true);
                }
            }
        });
    }

    private void setToken(){
        DAOFactory apidaoFactory = new APIDAOFactory();
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                token[0] = task.getResult();
                System.out.println(task.getResult());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        apidaoFactory.getFireBaseTokenDAO().addToken(task.getResult(), LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getId());
                        System.out.println("added token");
                    }
                }).start();
            }
        });
    }

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backGround = findViewById(R.id.activity_login_loading_background);
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
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backGround = findViewById(R.id.activity_login_loading_background);
        backGround.setVisibility(View.GONE);
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
