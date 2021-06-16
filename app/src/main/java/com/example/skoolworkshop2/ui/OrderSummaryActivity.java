package com.example.skoolworkshop2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.payment.MollieDAOFactory;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {
    private MollieDAOFactory mollieDAOFactory;
    private LinearLayout mPaymentMethodsLinearLayout;
    private LinearLayout mPaymentTransferButton;
    private LinearLayout mPaymentCjpButton;
    private LinearLayout mPaymentCashButton;
    private LinearLayout mPaymentIdealButton;
    private Spinner mIdealSpinner;
    private Button mOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        initializeAttributes();

        new Thread(() -> {
            mIdealSpinner.setAdapter(new BankArrayAdapter(getBaseContext(), mollieDAOFactory.getBankDAO().getAllBanks()));
        }).start();

        mPaymentTransferButton.setOnClickListener(this);
        mPaymentCjpButton.setOnClickListener(this);
        mPaymentCashButton.setOnClickListener(this);
        mPaymentIdealButton.setOnClickListener(this);

        mOrderButton.setText("Door naar betalen");
        mOrderButton.setOnClickListener(v -> {
            System.out.println("ORDERED!");
        });
    }

    @Override
    public void onClick (View v) {
        deselectPayment(mPaymentTransferButton);
        deselectPayment(mPaymentCjpButton);
        deselectPayment(mPaymentCashButton);
        deselectPayment(mPaymentIdealButton);

        switch (v.getId()) {
            case R.id.activity_summary_btn_transfer:
                selectPayment(mPaymentTransferButton);
                break;
            case R.id.activity_summary_btn_cjp:
                selectPayment(mPaymentCjpButton);
                break;
            case R.id.activity_summary_btn_cash:
                selectPayment(mPaymentCashButton);
                break;
            case R.id.activity_summary_btn_ideal:
                selectPayment(mPaymentIdealButton);
                break;
        }
    }

    private void initializeAttributes() {
        mollieDAOFactory = new MollieDAOFactory();
        mPaymentMethodsLinearLayout = findViewById(R.id.activity_summary_ll_payment_methods);
        mPaymentTransferButton = findViewById(R.id.activity_summary_btn_transfer);
        mPaymentCjpButton = findViewById(R.id.activity_summary_btn_cjp);
        mPaymentCashButton = findViewById(R.id.activity_summary_btn_cash);
        mPaymentIdealButton = findViewById(R.id.activity_summary_btn_ideal);
        mIdealSpinner = findViewById(R.id.component_btn_payment_spnr_ideal);
        mOrderButton = findViewById(R.id.activity_summary_btn_confirm);
    }

    private void deselectPayment (View v) {
        TextView mPaymentMethodTv = v.findViewById(R.id.component_btn_payment_tv_method);
        LinearLayout mPaymentExpandLl = v.findViewById(R.id.component_btn_payment_ll_expand);
        v.setSelected(false);
        mPaymentMethodTv.setTextColor(getColor(R.color.black));
        mPaymentExpandLl.setVisibility(View.GONE);
    }

    private void selectPayment (View v) {
        TextView mPaymentMethodTv = v.findViewById(R.id.component_btn_payment_tv_method);
        LinearLayout mPaymentExpandLl = v.findViewById(R.id.component_btn_payment_ll_expand);
        v.setSelected(true);
        mPaymentMethodTv.setTextColor(getColor(R.color.white));
        mPaymentExpandLl.setVisibility(View.VISIBLE);
    }
}