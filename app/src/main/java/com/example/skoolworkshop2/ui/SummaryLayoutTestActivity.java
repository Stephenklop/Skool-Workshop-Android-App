package com.example.skoolworkshop2.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;

public class SummaryLayoutTestActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mPaymentMethodsLl;

    LinearLayout mPaymentTransferBtn;
    LinearLayout mPaymentCjpBtn;
    LinearLayout mPaymentCashBtn;
    LinearLayout mPaymentIdealBtn;

    Spinner mIdealSpnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mPaymentMethodsLl = findViewById(R.id.activity_summary_ll_payment_methods);

        mPaymentTransferBtn = findViewById(R.id.activity_summary_btn_transfer);
        mPaymentCjpBtn = findViewById(R.id.activity_summary_btn_cjp);
        mPaymentCashBtn = findViewById(R.id.activity_summary_btn_cash);
        mPaymentIdealBtn = findViewById(R.id.activity_summary_btn_ideal);

        mIdealSpnr = findViewById(R.id.component_btn_payment_spnr_ideal);
        //mIdealSpnr.setAdapter(new WorkshopArrayAdapter(this, new String[]{"ABN Amro", "ASN Bank"}));

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
}
