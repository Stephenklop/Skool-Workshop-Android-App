package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.Workshop;

import java.util.ArrayList;
import java.util.List;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener {

    private String LOG_TAG = getClass().getSimpleName();
    private ImageButton mBackButton;
    private Button mSendBn;
    private EditText mPersonEditText;
    private EditText mDateEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private Spinner mCategorieSpinner;
    private Spinner mWorkshopSpinner;
    private ArrayAdapter<CharSequence> categorieArrayAdapter;
    private ArrayAdapter<Workshop> workshopArrayAdapter;
    private List<Workshop> workshopDummylist;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);

        workshopDummylist = new ArrayList<>();

        //add dummylist
        workshopDummylist.add(new Workshop(1, "Test", new String[]{"Test", "Inhoud", "Info", "kosten"}, 55.55, "11-11-2021", 25, Category.DS));
        workshopDummylist.add(new Workshop(1, "Test", new String[]{"Test", "Inhoud", "Info", "kosten"}, 55.55, "11-11-2021", 25, Category.BK));
        workshopDummylist.add(new Workshop(1, "Result", new String[]{"Test", "Inhoud", "Info", "kosten"}, 55.55, "11-11-2021", 25, Category.MK));

        // Setting up IDS
        mSendBn = findViewById(R.id.activity_cultureday_booking_btn_book);
        mBackButton = findViewById(R.id.activity_cultureday_booking_btn_back);
        mCategorieSpinner = findViewById(R.id.activity_cultureday_booking_spnr_category);
        mWorkshopSpinner = findViewById(R.id.activity_cultureday_booking_spnr_workshop);

        categorieArrayAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categorieArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workshopArrayAdapter = new ArrayAdapter<Workshop>(this, android.R.layout.simple_spinner_item, workshopDummylist);


        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
        mCategorieSpinner.setAdapter(categorieArrayAdapter);

        mSendBn.setText("Boek nu");

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
