package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.Validation.TextValidator;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener {

    private String LOG_TAG = getClass().getSimpleName();
    private ImageButton mBackButton;
    private EditText mPersonEditText;
    private EditText mDateEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_question);

        // Setting up IDS
        mBackButton = findViewById(R.id.activity_cultureday_question_btn_back);
        mPersonEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_amount);
//        mDateEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_date);
        mPhoneEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_phone);
        mEmailEditText = (EditText) findViewById(R.id.activity_cultureday_question_et_email);

        // Setting up validations
        mPersonEditText.addTextChangedListener(new TextValidator(mPersonEditText) {
            @Override
            public void validate(TextView textView, String text) {
                text = textView.getText().toString();
                if(text.length() >= 1){
                    Toast.makeText(getApplicationContext(), "Validation complete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error, only use numbers.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPhoneEditText.addTextChangedListener(new TextValidator(mPhoneEditText) {
            @Override
            public void validate(TextView textView, String text) {
                text = textView.getText().toString();
                Log.d(LOG_TAG, "validate: email:" + text);
                if(text.matches(" ")){
                    Toast.makeText(getApplicationContext(), "Validation complete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error, wrong phone input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEmailEditText.addTextChangedListener(new TextValidator(mEmailEditText) {
            @Override
            public void validate(TextView textView, String text) {
                text = textView.getText().toString();
                if(text.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(getApplicationContext(), "Validation complete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error, wrong email input", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
