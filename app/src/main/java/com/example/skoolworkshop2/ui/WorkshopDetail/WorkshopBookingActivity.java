package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.WorkshopParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.ShoppingCartLayoutTestActivity;

import org.w3c.dom.Text;

import java.time.LocalDate;

public class WorkshopBookingActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private String LOG_TAG = getClass().getSimpleName();
    // Buttons
    private Button mSendBn;
    private ImageButton mBackButton;
    // Validations
    private DateValidation dateValidation = new DateValidation();
    private LearningLevelValidator learningLevelValidator = new LearningLevelValidator();
    private MinuteValidator minuteValidator = new MinuteValidator();
    private WorkshopParticipantsValidator workshopParticipantsValidator = new WorkshopParticipantsValidator();
    private RoundsValidator roundsValidator = new RoundsValidator();
    // Layout
    private RelativeLayout mDateLayout;
    private RelativeLayout mParticipantsLayout;
    // Edit texts
    private EditText mLevelEditText;
    private EditText mDateEditText;
    private EditText mMinuteEditText;
    private EditText mParticipantsEditText;
    private EditText mRoundsEditText;
    private EditText mSchemeEditText;
    // Textviews
    private TextView mResultWorkshopRoundsTextView;
    private TextView mResultWorkshopMinutesPerRoundTextView;
    private TextView mResultWorkshopSchemeTextView;
    private TextView mResultWorkshopTotalMinutesTextView;
    private TextView mResultWorkshopLearningLevelTextView;
    private TextView mTotalCostTextView;

    private DatePickerDialog datePickerDialog;

    //Total time variables;
    private int minuteT;
    private int roundT;
    private int totalTime;
    // Total Cost
    private Double totalCost;
    private Double totalPartCost;
    // Workshop name
    private String workshop;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_booking);

//        datePickerDialog = new DatePickerDialog(this, WorkshopBookingActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        //assign id
        // Buttons
        mSendBn = findViewById(R.id.activity_workshop_booking_btn_book);
        mBackButton = findViewById(R.id.activity_workshop_booking_btn_back);
        // Date
        mDateLayout = findViewById(R.id.activity_workshop_booking_et_date);
        mDateEditText = findViewById(R.id.date_picker_edit_text);
        ImageButton datePickerButton = mDateLayout.findViewById(R.id.component_edittext_date_calendar_btn_calendar);
        // Learning Level
        mLevelEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_level);
        mResultWorkshopLearningLevelTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_level);
        // minutes
        mMinuteEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_mins);
        mResultWorkshopMinutesPerRoundTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_mins);
        // Workshop Participants
        mParticipantsLayout= findViewById(R.id.activity_workshop_booking_et_amount);
        mParticipantsEditText = findViewById(R.id.number_edit_text);
        // Rounds
        mRoundsEditText = (EditText) findViewById(R.id.activity_workshop_booking_et_rounds);
        mResultWorkshopRoundsTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_rounds);
        // Scheme
        mSchemeEditText = (EditText) findViewById(R.id.schedule_edit_text);
        mResultWorkshopSchemeTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_schedule);
        // Total cost
        mTotalCostTextView = (TextView) findViewById(R.id.activity_workshop_booking_tv_subtotal);
        //Total time
        this.minuteT = 0;
        this.roundT = 0 ;
        this.totalTime = 0;
        this.totalCost = 0.0;
        // Workshop name
        workshop = getIntent().getStringExtra("NAME");
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

        //Total time
        mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");

        // Minutes
        mMinuteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mMinuteEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!minuteValidator.isValidMinute(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_error);
                    // totalCosts
                    int participants = Integer.valueOf(mParticipantsEditText.getText().toString());
                    totalPartCost = (participants * 7.50);
                    totalCost += totalPartCost;
                    mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (minuteValidator.isValidMinute(editable.toString())){
                    int minutes = Integer.parseInt(editable.toString());
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: " + minutes);
                    minuteT = minutes;
                    // totalCosts
                    totalTime = minuteT * roundT;
                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + totalTime + " minuten.");
                    totalCost += (totalTime * 2.50);
                    mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                    minuteValidator.mIsValid = true;
                } else {
                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: ");
                }
            }
        });

        // Participants
        mParticipantsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!workshopParticipantsValidator.isValidMaxParticipant(charSequence.toString())) {
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                    // totalCosts
                    int participants = 0;
                    if (!mParticipantsEditText.getText().toString().isEmpty()) {
                        participants = Integer.valueOf(mParticipantsEditText.getText().toString());
                        totalPartCost = (participants * 7.50);
                        totalCost += totalPartCost;
                        mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                    } else {
                        totalPartCost = 0.0;
                        totalCost -= totalCost;
                        mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (workshopParticipantsValidator.isValidMaxParticipant(editable.toString())){
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    if (workshop.equals("Graffiti") || workshop.equals("T-shirt Ontwerpen")){
                        int participants = Integer.valueOf(mParticipantsEditText.getText().toString());
                        totalPartCost = (participants * 7.50);
                        totalCost += totalPartCost;
                        mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                    }
                    workshopParticipantsValidator.mIsValid = true;
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

                mRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!roundsValidator.isValidWorkshopRounds(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
                    // totalCosts
                    int participants = Integer.valueOf(mParticipantsEditText.getText().toString());
                    totalPartCost = (participants * 7.50);
                    totalCost += totalPartCost;
                    mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (roundsValidator.isValidWorkshopRounds(editable.toString())){
                    int rounds = Integer.parseInt(editable.toString());
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: " + rounds);
                    roundT = rounds;
                    totalTime = minuteT * roundT;
                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + totalTime + " minuten.");
                    // totalCosts
                    totalCost += (totalTime * 2.50);
                    mTotalCostTextView.setText("Subtotaal: €" + totalCost);
                    roundsValidator.mIsValid = true;
                } else {
                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: ");
                }
            }
        });

        //Schedule scheme
        mSchemeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchemeEditText.setBackgroundResource(R.drawable.edittext_focused);


            }

            @Override
            public void afterTextChanged(Editable s) {

                String scheme = s.toString();
                mSchemeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                mResultWorkshopSchemeTextView.setText("Tijdschema: " + scheme);

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
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (learningLevelValidator.isValidLearningLevels(s.toString())){

                    mLevelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mResultWorkshopLearningLevelTextView.setText("Leerniveau: " + s.toString());
                    learningLevelValidator.mIsValid = true;
                } else {
                    mResultWorkshopLearningLevelTextView.setText("Leerniveau: ");
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
                    Intent intent = new Intent(getApplicationContext(), ShoppingCartLayoutTestActivity.class);
                    StringBuilder stb = new StringBuilder();
                    stb.append(mDateEditText.getText());
                    stb.append(mParticipantsEditText.getText());
                    stb.append(mRoundsEditText.getText());
                    stb.append(mMinuteEditText.getText());
                    stb.append(mLevelEditText.getText());

                    intent.putExtra(Intent.EXTRA_TEXT, stb.toString());
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
}

