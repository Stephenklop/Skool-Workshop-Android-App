package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopDetailActivity;

public class activity_email_result extends AppCompatActivity {
    private ImageView mResponseImg;
    private TextView mResponseTv;

    private Button mContinueBtn;

    private Product workshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_result);

        workshop = (Product) getIntent().getSerializableExtra("workshop");
        boolean failed = getIntent().getBooleanExtra("failed", false);

        mResponseImg = findViewById(R.id.activity_mollie_result_img_response);
        mResponseTv = findViewById(R.id.activity_mollie_result_tv_response);
        mContinueBtn = findViewById(R.id.activity_mollie_result_btn_continue);

        mContinueBtn.setText("Terug naar de workshop");
        mContinueBtn.setOnClickListener(v -> {
            startActivity(new Intent(activity_email_result.this, WorkshopDetailActivity.class).putExtra("workshop", workshop));
        });

        if(failed){
            failureAnim();
        } else {
            successAnim();
        }
    }

    private void successAnim() {
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_success);
        mResponseTv.setText("Uw mail is verzonden");
        mResponseImg.setImageDrawable(avd);
        avd.start();
    }

    private void failureAnim() {
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_fail);
        mResponseTv.setText("Er is iets misgegaan tijdens het verzenden van uw mail");
        mResponseImg.setImageDrawable(avd);
        avd.start();
    }
}