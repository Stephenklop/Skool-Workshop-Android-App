package com.example.skoolworkshop2.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.payment.MollieDAOFactory;
import com.example.skoolworkshop2.domain.Bank;

import java.util.List;

public class SummaryLayoutTestActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mPaymentMethodsLl;

    LinearLayout mPaymentTransferBtn;
    LinearLayout mPaymentCjpBtn;
    LinearLayout mPaymentCashBtn;
    LinearLayout mPaymentIdealBtn;

    Spinner mIdealSpnr;

    Button mConfirmBtn;

    private List<Bank> banks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mPaymentMethodsLl = findViewById(R.id.activity_summary_ll_payment_methods);

        mPaymentTransferBtn = findViewById(R.id.activity_summary_btn_transfer);
        mPaymentCjpBtn = findViewById(R.id.activity_summary_btn_cjp);
        mPaymentCashBtn = findViewById(R.id.activity_summary_btn_cash);
        mPaymentIdealBtn = findViewById(R.id.activity_summary_btn_ideal);

        new Thread(() -> {
            getBanks();

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mIdealSpnr = findViewById(R.id.component_btn_payment_spnr_ideal);
                    mIdealSpnr.setAdapter(new BankArrayAdapter(getApplicationContext(), banks));
                }
            });
        }).start();


        mPaymentTransferBtn.setOnClickListener(this);
        mPaymentCjpBtn.setOnClickListener(this);
        mPaymentCashBtn.setOnClickListener(this);
        mPaymentIdealBtn.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        deselectPayment(mPaymentTransferBtn);
        deselectPayment(mPaymentCjpBtn);
        deselectPayment(mPaymentCashBtn);
        deselectPayment(mPaymentIdealBtn);

        switch (v.getId()) {
            case R.id.activity_summary_btn_transfer:
                selectPayment(mPaymentTransferBtn);
                break;
            case R.id.activity_summary_btn_cjp:
                selectPayment(mPaymentCjpBtn);
                break;
            case R.id.activity_summary_btn_cash:
                selectPayment(mPaymentCashBtn);
                break;
            case R.id.activity_summary_btn_ideal:
                selectPayment(mPaymentIdealBtn);
                break;
        }
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

    private void getBanks() {
        MollieDAOFactory mollieDAOFactory = new MollieDAOFactory();

        this.banks = mollieDAOFactory.getBankDAO().getAllBanks();
    }
}
