package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.ui.MainActivity;

public class WorkshopQuestionActivity extends FragmentActivity implements View.OnClickListener {
    private Button mSendBn;
    private ImageButton mBackButton;
    private EditText mEmailEditText;
    private EmailValidator emailValidator = new EmailValidator();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_question);
        mSendBn = findViewById(R.id.activity_workshop_question_btn_send);
        mBackButton = findViewById(R.id.activity_workshop_question_btn_back);

        mEmailEditText = (EditText) findViewById(R.id.activity_workshop_question_et_email);
        mEmailEditText.addTextChangedListener(emailValidator);

        mSendBn.setText("Verzenden");

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

