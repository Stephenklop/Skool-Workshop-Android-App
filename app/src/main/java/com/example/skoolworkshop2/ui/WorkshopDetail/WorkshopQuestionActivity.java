package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.ui.MainActivity;

public class WorkshopQuestionActivity extends FragmentActivity implements View.OnClickListener {
    private Button mSendBn;
    private ImageButton mBackButton;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_question);

        mSendBn = findViewById(R.id.activity_workshop_question_btn_send);
        mBackButton = findViewById(R.id.activity_workshop_question_btn_back);

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

