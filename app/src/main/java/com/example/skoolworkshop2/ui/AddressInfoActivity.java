package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;

public class AddressInfoActivity extends AppCompatActivity {
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mCompanyNameEditText;
    private Spinner mCountrySpinner;
    private Drawable mCountryNL;
    private Drawable mCountryBE;
    private EditText mPostalCodeEditText;
    private EditText mHouseNumberEditText;
    private EditText mCityEditText;
    private EditText mStreetEditText;
    private EditText mPhoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mCjpNumberEditText;
    private CheckBox mWorkshopLocationCheckBox;
    private EditText mWorkshopInformation;
    private ConstraintLayout mWorkshopLocationConstraintLayout;
    private Button mOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        initializeAttributes();

        mCountrySpinner.setAdapter(new CountryArrayAdapter(this, new Drawable[]{mCountryNL, mCountryBE}));

        mWorkshopLocationCheckBox.setOnClickListener(v -> {
                if (mWorkshopLocationConstraintLayout.getVisibility() == View.VISIBLE) {
                    mWorkshopLocationConstraintLayout.setVisibility(View.GONE);
                } else {
                    mWorkshopLocationConstraintLayout.setVisibility(View.VISIBLE);
                }
        });

        mOrderButton.setText("Verder met bestellen");
        mOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderSummaryActivity.class);
            startActivity(intent);
        });
    }

    private void initializeAttributes() {
        mFirstNameEditText = findViewById(R.id.activity_address_info_et_firstname);
        mLastNameEditText = findViewById(R.id.activity_address_info_et_lastname);
        mCompanyNameEditText = findViewById(R.id.activity_address_info_et_company);
        mCountrySpinner = findViewById(R.id.activity_address_info_spnr_country);
        mCountryNL = getDrawable(R.drawable.ic_flag_of_the_netherlands);
        mCountryBE = getDrawable(R.drawable.ic_flag_of_belgium);
        mPostalCodeEditText = findViewById(R.id.activity_address_info_et_postalcode);
        mHouseNumberEditText = findViewById(R.id.activity_address_info_housenr);
        mCityEditText = findViewById(R.id.activity_address_info_et_place);
        mStreetEditText = findViewById(R.id.activity_address_info_et_street);
        mPhoneNumberEditText = findViewById(R.id.activity_address_info_et_tel);
        mEmailEditText = findViewById(R.id.activity_address_info_et_email);
        mCjpNumberEditText = findViewById(R.id.activity_address_info_et_cjp);
        mWorkshopInformation = findViewById(R.id.activity_address_info_et_info);
        mWorkshopLocationCheckBox = findViewById(R.id.activity_address_info_cb_workshop_location);
        mWorkshopLocationConstraintLayout = findViewById(R.id.activity_address_info_cl_workshop_location);
        mOrderButton = findViewById(R.id.activity_address_info_btn_submit);
    }
}
