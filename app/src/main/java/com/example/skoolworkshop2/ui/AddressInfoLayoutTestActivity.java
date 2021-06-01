package com.example.skoolworkshop2.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.skoolworkshop2.R;

public class AddressInfoLayoutTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);

        Drawable NL = this.getDrawable(R.drawable.ic_flag_of_the_netherlands);
        Drawable BE = this.getDrawable(R.drawable.ic_flag_of_belgium);

        Spinner mLocationCountrySpnr = findViewById(R.id.activity_address_info_spnr_country);
        mLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Drawable[]{NL, BE}));
        mLocationCountrySpnr.setSelection(1);

        Spinner mWorkshopLocationCountrySpnr = findViewById(R.id.activity_address_info_spnr_workshop_country);
        mWorkshopLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Drawable[]{NL, BE}));
        mWorkshopLocationCountrySpnr.setSelection(1);

        CheckBox mWorkshopLocationCb = findViewById(R.id.activity_address_info_cb_workshop_location);
        ConstraintLayout mWorkshopLocationCl = findViewById(R.id.activity_address_info_cl_workshop_location);

        mWorkshopLocationCb.setOnClickListener((View v) -> {
            if (mWorkshopLocationCl.getVisibility() == View.VISIBLE) {
                mWorkshopLocationCl.setVisibility(View.GONE);
            } else {
                mWorkshopLocationCl.setVisibility(View.VISIBLE);
            }
        });
    }
}
