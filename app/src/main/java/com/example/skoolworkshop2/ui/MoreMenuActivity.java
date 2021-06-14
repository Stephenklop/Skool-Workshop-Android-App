package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.QuizAPIService;
import com.example.skoolworkshop2.domain.Quiz;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.User.AccountActivity;
import com.example.skoolworkshop2.ui.User.MyAccountActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MoreMenuActivity extends AppCompatActivity {
    private String LOG_TAG = getClass().getSimpleName();

    private AppCompatButton mQuizButton;
    private AppCompatButton mAskedQuestionsButton;
    private AppCompatButton mAboutUsButton;
    private AppCompatButton mAccountbutton;

    // List
    private List<Quiz> quizzes;
    private QuizAPIService quizAPIService;
    private String quizUrl;

    private MenuController controller;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        View root = (View) findViewById(R.id.activity_more);
        controller = new MenuController(root);

        BottomNavigationView menu = findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(4).setChecked(true);

        quizzes = new ArrayList<>();
        quizAPIService = new QuizAPIService();

        Thread loadQuiz = new Thread(() -> {
           quizzes.addAll(quizAPIService.getAllQuizzes());
        });
        try {
            loadQuiz.join();
            loadQuiz.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "onCreate: quizzes: " + quizzes);
        mAccountbutton = findViewById(R.id.activity_more_btn_account);
        mAboutUsButton = findViewById(R.id.activity_more_btn_about);
        mAskedQuestionsButton = findViewById(R.id.activity_more_btn_faq);
        mQuizButton = findViewById(R.id.activity_more_btn_quiz);

        mAccountbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                UserManager um = new UserManager(getApplication());
                if(um.hasInfo()){
                    Bundle bundle = new Bundle();
                    bundle.putString("USERNAME", um.getInfo().getUsername());
                    startActivity(new Intent(getApplicationContext(), MyAccountActivity.class).putExtras(bundle));
                } else {
                    Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(accountIntent);
                }
            }
        });

        mAboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                aboutIntent.putExtra("url", "https://skoolworkshop.nl/over-ons/");
                startActivity(aboutIntent);
            }
        });

        mAskedQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faqIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                faqIntent.putExtra("url", "https://skoolworkshop.nl/over-ons/#:~:text=Belangrijke,aanbod");
                startActivity(faqIntent);
            }
        });

        if(quizzes.size() == 0){
            mQuizButton.setEnabled(false);
            mQuizButton.setBackgroundColor(getResources().getColor(R.color.disabled_grey));
        }
        mQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i < quizzes.size(); i++){
                    if (quizzes.get(i).isStatus()){
                       quizUrl = quizzes.get(i).getUrl();
                    }
                }

                String url = quizzes.get(3).getUrl();

                Intent quizIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + url));
                startActivity(quizIntent);
            }
        });
    }
}
