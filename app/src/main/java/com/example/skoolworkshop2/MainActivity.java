package com.example.skoolworkshop2;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);

        Spinner mCategorySpnr = findViewById(R.id.activity_cultureday_booking_spnr_category);
        mCategorySpnr.setAdapter(new WorkshopArrayAdapter(this, new String[]{"Beeldende kunst", "Theater"}));

        Spinner mWorkshopSpnr = findViewById(R.id.activity_cultureday_booking_spnr_workshop);
        mWorkshopSpnr.setAdapter(new WorkshopArrayAdapter(this, new String[]{"workshop", "workshop", "workshop", "workshop", "workshop"}));

    }
}