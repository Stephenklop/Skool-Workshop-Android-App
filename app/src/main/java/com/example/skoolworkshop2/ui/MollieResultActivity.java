package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.payment.MollieDAOFactory;
import com.example.skoolworkshop2.domain.Payment;

public class MollieResultActivity extends AppCompatActivity {
    private MollieDAOFactory mMollieDAOFactory;
    private Payment mPayment;
    private ImageView mResponseImg;
    private TextView mResponseTv;

    private Button mContinueBtn;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mollie_result);

        mMollieDAOFactory = new MollieDAOFactory();
        mPayment = LocalDb.getDatabase(getBaseContext()).getPaymentDAO().getPayment();

        mResponseImg = findViewById(R.id.activity_mollie_result_img_response);
        mResponseTv = findViewById(R.id.activity_mollie_result_tv_response);
        mContinueBtn = findViewById(R.id.activity_mollie_result_btn_continue);

        mContinueBtn.setText("Terug naar de app");
        mContinueBtn.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(homeIntent);
        });

        new Thread(() -> {
            mPayment = mMollieDAOFactory.getPaymentDAO().getPayment(mPayment.getId());

            if (mPayment != null && mPayment.getStatus().equals("paid")) {
                runOnUiThread(() -> successAnim());
            } else {
                runOnUiThread(() -> failureAnim());
            }
        }).start();
    }

    private void successAnim() {
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_success);
        mResponseTv.setText("Uw betaling is voltooid");
        mResponseImg.setImageDrawable(avd);
        avd.start();
    }

    private void failureAnim() {
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_fail);
        mResponseTv.setText("Er is iets misgegaan tijdens uw betaling");
        mResponseImg.setImageDrawable(avd);
        avd.start();
    }
}
