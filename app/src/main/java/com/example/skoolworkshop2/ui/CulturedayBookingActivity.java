package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton mBackButton;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_question);

        mBackButton = findViewById(R.id.activity_cultureday_question_btn_back);

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
