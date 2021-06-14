package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.WorkshopParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.shoppingCart.ShoppingCartActivity;

import java.time.LocalDate;

import io.paperdb.Paper;

public class WorkshopBookingActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private String LOG_TAG = getClass().getSimpleName();
    private LocalAppStorage localAppStorage;
    private WorkshopItem workshop;
    private ImageButton mBackButton;
    private ImageView mWorkshopBanner;
    private TextView mTitle;
    private RelativeLayout mDateLayout;
    private EditText mDateEditText;
    private DatePickerDialog datePickerDialog;
    private RelativeLayout mParticipantsLayout;
    private RelativeLayout mSchemeLayout;
    private EditText mParticipantsEditText;
    private EditText mRoundsEditText;
    private EditText mMinuteEditText;
    private EditText mSchemeEditText;
    private EditText mLevelEditText;
    private TextView mResultWorkshopRoundsTextView;
    private TextView mResultWorkshopMinutesPerRoundTextView;
    private TextView mResultWorkshopSchemeTextView;
    private TextView mResultWorkshopTotalMinutesTextView;
    private TextView mResultWorkshopLearningLevelTextView;
    private TextView mTotalCostTextView;
    private Button mSendBn;

    // Validators
    private DateValidation dateValidation = new DateValidation();
    private LearningLevelValidator learningLevelValidator = new LearningLevelValidator();
    private MinuteValidator minuteValidator = new MinuteValidator();
    private WorkshopParticipantsValidator workshopParticipantsValidator = new WorkshopParticipantsValidator();
    private RoundsValidator roundsValidator = new RoundsValidator();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_booking);

        localAppStorage = new LocalAppStorage(getBaseContext());

        // Initialize workshop
        workshop = (WorkshopItem) getIntent().getSerializableExtra("workshop");

        // Set title
        mTitle = findViewById(R.id.activity_workshop_booking_tv_title);
        mTitle.setText(workshop.getProduct().getName());

        mWorkshopBanner = findViewById(R.id.activity_workshop_booking_img_banner);
        Glide.with(getBaseContext()).load(workshop.getProduct().getSourceImage()).centerCrop().into(mWorkshopBanner);

        // Buttons
        mSendBn = findViewById(R.id.activity_workshop_booking_btn_book);
        mBackButton = findViewById(R.id.activity_workshop_booking_btn_back);
        // Date
        mDateLayout = findViewById(R.id.activity_workshop_booking_et_date);
        mDateEditText = findViewById(R.id.date_picker_edit_text);
        datePickerDialog = new DatePickerDialog(this, WorkshopBookingActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        ImageButton datePickerButton = mDateLayout.findViewById(R.id.component_edittext_date_calendar_btn_calendar);
        // Learning Level
        mLevelEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_level);
        mResultWorkshopLearningLevelTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_level);
        // minutes
        mMinuteEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_mins);

        // Change workshop duration
        //workshop.setDuration(Integer.parseInt(mMinuteEditText.getText().toString()));
        mResultWorkshopMinutesPerRoundTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_mins);

        // Workshop Participants
        mParticipantsLayout= findViewById(R.id.activity_workshop_booking_et_amount);
        ImageButton particpantsInfoBtn = mParticipantsLayout.findViewById(R.id.component_edittext_number_info_btn_info);
        particpantsInfoBtn.setOnClickListener(v -> {
            String header = "Totaal aantal deelnemers";
            String content = "Maximaal 25 deelnemers per workshop. \n\n€7,50 extra kosten per deelnemer voor Workshops Graffiti en Workshops T-Shirt Ontwerpen";
            new RoundedDialog(WorkshopBookingActivity.this, header, content);
        });
        mParticipantsEditText = findViewById(R.id.number_edit_text);
        mParticipantsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                workshop.setParticipants(Integer.parseInt(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (workshopParticipantsValidator.isValidMaxParticipant(editable.toString())) {
                    updateOrderOverview();
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    workshopParticipantsValidator.mIsValid = true;
                } else if (!workshopParticipantsValidator.isValidMaxParticipant(editable.toString())) {
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                } else {
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        // Rounds
        mRoundsEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_rounds);
        mResultWorkshopRoundsTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_rounds);
        // Scheme

        mSchemeEditText = (EditText) findViewById(R.id.schedule_edit_text);
        mSchemeLayout = findViewById(R.id.activity_workshop_booking_et_schedule);
        ImageButton schemeInfoBtn = mSchemeLayout.findViewById(R.id.component_edittext_plaintext_info_multiline_btn_info);
        schemeInfoBtn.setOnClickListener(v -> {
            String header = "Tijdschema";
            String content = "Geef hier op hoe jullie het tijdschema willen hebben (aantal rondes met eventueel pauzes)";
            new RoundedDialog(WorkshopBookingActivity.this, header, content);
        });
        mResultWorkshopSchemeTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_schedule);
        // Total cost
        mTotalCostTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_subtotal);
        // Total time
        mResultWorkshopTotalMinutesTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_duration);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //Use validator
        // Date Validator
        mDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDateEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!dateValidation.isValidDate(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mDateEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (dateValidation.isValidDate(editable.toString())){
                    mDateEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    dateValidation.mIsValid = true;
                }
            }
        });

        // Rounds
        mRoundsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                workshop.setRounds(Integer.parseInt(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (roundsValidator.isValidWorkshopRounds(editable.toString())) {
                    updateOrderOverview();
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    roundsValidator.mIsValid = true;
                } else if (!roundsValidator.isValidWorkshopRounds(editable.toString())) {
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
                    mTotalCostTextView.setText("Subtotaal: €");
                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");
                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: ");
                } else {
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
                    mTotalCostTextView.setText("Subtotaal: €");
                }
            }
        });

        //Total time
        mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");

        // Minutes
        mMinuteEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (minuteValidator.isValidMinute(editable.toString())) {
                    updateOrderOverview();
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    minuteValidator.mIsValid = true;
                } else if (!minuteValidator.isValidMinute(editable.toString())){
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_error);
                    mTotalCostTextView.setText("Subtotaal: €");
                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");
                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: ");
                } else {
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_focused);
                    mTotalCostTextView.setText("Subtotaal: €");
                }
            }
        });

        // Participants

        //Schedule scheme
        mSchemeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchemeEditText.setBackgroundResource(R.drawable.edittext_focused);
                workshop.setTimeSchedule(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateOrderOverview();
                mSchemeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
            }
        });

        //level
        mLevelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLevelEditText.setBackgroundResource(R.drawable.edittext_focused);
                if(!learningLevelValidator.isValidLearningLevels(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mLevelEditText.setBackgroundResource(R.drawable.edittext_error);
                } else {
                    workshop.setLearningLevel(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (learningLevelValidator.isValidLearningLevels(s.toString())){
                    updateOrderOverview();
                    learningLevelValidator.mIsValid = true;
                    mLevelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                }
            }
        });

        // Set texts
        mSendBn.setText("Boek nu");
        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Datum, deelnemers, rondes, minuten, learning levels niet leeg, rest wel
                if(dateValidation.isValid() && workshopParticipantsValidator.isValid() && roundsValidator.isValid() && minuteValidator.isValid() && learningLevelValidator.isValid()){
                    // Add workshop to local storage
                    localAppStorage.addToList("cartItems", workshop);
                    System.out.println("CART ITEMS BOOKING: " + Paper.book().read("cartItems"));

                    // Initiate and start intent
                    Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Een van uw verplichte velden is nog leeg!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Set on click listener
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(dayOfMonth < 10 && month < 10){
            mDateEditText.setText("0" + dayOfMonth + "/0" + month + "/" + year);
        } else if (dayOfMonth < 10){
            mDateEditText.setText("0" + dayOfMonth + "/" + month + "/" + year);
        } else if (month < 10){
            mDateEditText.setText(dayOfMonth + "/0" + month + "/" + year);
        } else {
            mDateEditText.setText(dayOfMonth + "/" + month + "/" + year);
        }
        datePickerDialog.cancel();
    }

    private void updateOrderOverview() {
        mResultWorkshopRoundsTextView.setText("Workshoprondes: " + workshop.getRounds());
        mResultWorkshopMinutesPerRoundTextView.setText("Duur per workshopronde: " + workshop.getRoundDuration() + " min");
        mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + workshop.getRoundDuration() + " min");
        mResultWorkshopSchemeTextView.setText("Tijdschema: " + ((workshop.getTimeSchedule() == null || workshop.getTimeSchedule().equals("")) ? "n.n.g." : workshop.getTimeSchedule()));
        mResultWorkshopLearningLevelTextView.setText("Leerniveau: " + ((workshop.getLearningLevel() == null || workshop.getLearningLevel().equals("")) ? "n.n.b." : workshop.getLearningLevel()));
        System.out.println("TOTALE PRIJS: " + workshop.getPrice());
        mTotalCostTextView.setText("Subtotaal: €" + (int) workshop.getPrice());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}

