package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.skoolworkshop2.R;


public class MoreMenuActivity extends AppCompatActivity {
    private AppCompatButton mQuizButton;
    private AppCompatButton mAskedQuestionsButton;
    private AppCompatButton mAboutUsButton;
    private AppCompatButton mAccountbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mAccountbutton = findViewById(R.id.activity_more_btn_account);
        mAboutUsButton = findViewById(R.id.activity_more_btn_about);
        mAskedQuestionsButton = findViewById(R.id.activity_more_btn_faq);
        mQuizButton = findViewById(R.id.activity_more_btn_quiz);

        mAccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent accountIntent = new Intent(this, )
            }
        });

    }
}
