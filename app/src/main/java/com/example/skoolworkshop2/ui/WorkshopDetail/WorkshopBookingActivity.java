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
import android.view.animation.AnimationUtils;
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
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.WorkshopParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.StreetnameValidator;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.example.skoolworkshop2.ui.shoppingCart.ShoppingCartActivity;

import java.time.LocalDate;
import java.util.Date;

import io.paperdb.Paper;

public class WorkshopBookingActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
    private String LOG_TAG = getClass().getSimpleName();
    private LocalAppStorage localAppStorage;
    private Product workshop;
    private WorkshopItem workshopItem;
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
    private TextView mErrTv;
    private Button mPriceBn;
    private Button mParticipantsBn;
    private Button mDurationBn;
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

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        localAppStorage = new LocalAppStorage(getBaseContext());

        // Initialize workshop product
        workshop = (Product) getIntent().getSerializableExtra("workshop");

        // Initialize workshop item
        workshopItem = new WorkshopItem(workshop);

        // Set title
        mTitle = findViewById(R.id.activity_workshop_booking_tv_title);
        mTitle.setText(workshop.getName());

        mWorkshopBanner = findViewById(R.id.activity_workshop_booking_img_banner);
        Glide.with(getBaseContext()).load(workshop.getSourceImage()).centerCrop().into(mWorkshopBanner);

        // Buttons
        mSendBn = findViewById(R.id.activity_workshop_booking_btn_book);
        mBackButton = findViewById(R.id.activity_workshop_booking_btn_back);
        // Date
        mDateLayout = findViewById(R.id.activity_workshop_booking_et_date);
        mDateEditText = findViewById(R.id.date_picker_edit_text);
        datePickerDialog = new DatePickerDialog(this, R.style.Theme_SkoolWorkshop2_DatePicker, WorkshopBookingActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
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
                if(!mParticipantsEditText.equals("")) {
                    if (workshopParticipantsValidator.isValidMaxParticipant(charSequence.toString())) {
                        updateOrderOverview();
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        workshopParticipantsValidator.mIsValid = true;
                    } else if (!workshopParticipantsValidator.isValidMaxParticipant(charSequence.toString())) {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                        workshopParticipantsValidator.mIsValid = false;
                    } else {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        mParticipantsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(workshopParticipantsValidator.isValid()) {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
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

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        mDateEditText.setFocusable(false);

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
                if(!mDateEditText.equals("")) {
                    if (dateValidation.isValidDate(charSequence.toString())) {
                        mDateEditText.setBackgroundResource(R.drawable.edittext_default);
                        dateValidation.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mDateEditText.setBackgroundResource(R.drawable.edittext_error);
                        dateValidation.mIsValid = false;

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                        mDateEditText.setBackgroundResource(R.drawable.edittext_default);
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
                workshopItem.setRounds(Integer.parseInt((charSequence.toString().equals("")) ? "0" : charSequence.toString()));
                if(!mRoundsEditText.equals("")) {
                    if (roundsValidator.isValidWorkshopRounds(charSequence.toString())) {
                        updateOrderOverview();
                        mRoundsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        roundsValidator.mIsValid = true;
                    } else if (!roundsValidator.isValidWorkshopRounds(charSequence.toString())) {
                        mRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
                        mTotalCostTextView.setText("Subtotaal: €");
                        mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");
                        mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: ");
                        roundsValidator.mIsValid = false;
                    } else {
                        mRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
                        mTotalCostTextView.setText("Subtotaal: €");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mRoundsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(roundsValidator.isValid()) {
                        mRoundsEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
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
                if(!mMinuteEditText.equals("")) {
                    if (minuteValidator.isValidMinute(s.toString())) {
                        workshopItem.setRoundDuration(Integer.valueOf(s.toString()));
                        updateOrderOverview();
                        mMinuteEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        minuteValidator.mIsValid = true;
                    } else if (!minuteValidator.isValidMinute(s.toString())){
                        mMinuteEditText.setBackgroundResource(R.drawable.edittext_error);
                        mTotalCostTextView.setText("Subtotaal: €");
                        mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");
                        mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: ");
                        minuteValidator.mIsValid = false;
                    } else {
                        mMinuteEditText.setBackgroundResource(R.drawable.edittext_focused);
                        mTotalCostTextView.setText("Subtotaal: €");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mMinuteEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(minuteValidator.isValid()) {
                        mMinuteEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_focused);
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
                workshopItem.setTimeSchedule(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateOrderOverview();
                mSchemeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
            }
        });
        mSchemeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                        mSchemeEditText.setBackgroundResource(R.drawable.edittext_default);

                } else{
                    mSchemeEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        //level
        mLevelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mLevelEditText.equals("")) {
                    if (learningLevelValidator.isValidLearningLevels(s.toString())){
                        updateOrderOverview();
                        learningLevelValidator.mIsValid = true;
                        mLevelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        workshopItem.setLearningLevel(s.toString());

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mDateEditText.setBackgroundResource(R.drawable.edittext_error);
                        learningLevelValidator.mIsValid = false;

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLevelEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(learningLevelValidator.isValid()) {
                        mLevelEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mLevelEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        // Error
        mErrTv = findViewById(R.id.activity_workshop_booking_tv_err);

        // Set texts
        mSendBn.setText("Boek nu");
        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Datum, deelnemers, rondes, minuten, learning levels niet leeg, rest wel
                if(dateValidation.isValid() && workshopParticipantsValidator.isValid() && roundsValidator.isValid() && minuteValidator.isValid() && learningLevelValidator.isValid()){
                    mErrTv.setVisibility(View.GONE);
                    // Add workshop to local storage
                    localAppStorage.addToList("cartItems", workshop);
                    System.out.println("CART ITEMS BOOKING: " + Paper.book().read("cartItems"));

                    // Initiate and start intent
                    Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                    startActivity(intent);
                } else {
                    if (!dateValidation.isValid()) {
                        mDateEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                    if (!workshopParticipantsValidator.isValid()) {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                    if (!roundsValidator.isValid()) {
                        mRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                    if (!minuteValidator.isValid()) {
                        mMinuteEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                    if (!learningLevelValidator.isValid()) {
                        mLevelEditText.setBackgroundResource(R.drawable.edittext_error);
                    }

                    mErrTv.setVisibility(View.VISIBLE);
                    mErrTv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tv_err_translate_anim));
                }

            }
        });

        // Set on click listener
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSendBn.setOnClickListener(v -> {
            System.out.println("ORDER: " + workshopItem);
            // Save the item in the shopping cart
            LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().insertItemInShoppingCart(
                    new ShoppingCartItem(
                            workshopItem.getProduct().getProductId(),
                            true, workshopItem.getDate(),
                            workshopItem.getRounds(),
                            -1,
                            workshopItem.getRoundDuration(),
                            workshopItem.getTimeSchedule(),
                            workshopItem.getParticipants(),
                            0,
                            workshopItem.getLearningLevel(),
                            workshopItem.getPrice()
                    )
            );

            // Redirect to shopping cart
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        });
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
        mResultWorkshopRoundsTextView.setText("Workshoprondes: " + workshopItem.getRounds());
        mResultWorkshopMinutesPerRoundTextView.setText("Duur per workshopronde: " + workshopItem.getRoundDuration() + " min");
        mResultWorkshopSchemeTextView.setText("Tijdschema: " + ((workshopItem.getTimeSchedule() == null || workshopItem.getTimeSchedule().equals("")) ? "n.n.g." : workshopItem.getTimeSchedule()));
        mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + workshopItem.getRoundDuration() * workshopItem.getRounds() + " min");
        mResultWorkshopLearningLevelTextView.setText("Leerniveau: " + ((workshopItem.getLearningLevel() == null || workshopItem.getLearningLevel().equals("")) ? "n.n.b." : workshopItem.getLearningLevel()));
        System.out.println("TOTALE PRIJS: " + workshopItem.getPrice());
        mTotalCostTextView.setText("Subtotaal: €" + (int) workshopItem.getPrice());
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

