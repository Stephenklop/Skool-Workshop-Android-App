package com.example.skoolworkshop2.ui.cultureDay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantsItemValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.logic.validation.WorkshopsPerRoundValidator;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.ShoppingCartLayoutTestActivity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private String LOG_TAG = getClass().getSimpleName();
    private ImageButton mBackButton;
    private Button mSendBn;

    // Validations
    private DateValidation dateValidation = new DateValidation();
    private CultureDayParticipantsValidator cultureDayParticipantsValidator = new CultureDayParticipantsValidator();
    private RoundsValidator roundsValidator = new RoundsValidator();
    private WorkshopsPerRoundValidator workshopsPerRoundValidator = new WorkshopsPerRoundValidator();
    private MinuteValidator minuteValidator = new MinuteValidator();
    private LearningLevelValidator learningLevelValidator = new LearningLevelValidator();
    private ParticipantsItemValidator participantsItemValidator = new ParticipantsItemValidator();

    // Layout
    private RelativeLayout mDateLayout;
    private RelativeLayout mParticipantsLayout;
    private RelativeLayout mItemParticipantsLayout;
    private RelativeLayout mResultWorkshopPerRoundLayout;
    private RelativeLayout mResultWorkshopSchemeLayout;
    // Edit texts
    private EditText mDateEditText;
    private EditText mParticipantsEditText;
    private EditText mRoundsEditText;
    private EditText mWorkshopsPerRoundEditText;
    private EditText mMinuteEditText;
    private EditText mLevelEditText;
    private EditText mParticipantsItemEditText;
    private EditText mSchemeEditText;
    // Textviews
    private TextView mResultWorkshopRoundsTextView;
    private TextView mResultWorkshopMinutesPerRoundTextView;
    private TextView mResultWorkshopSchemeTextView;
    private TextView mResultWorkshopTotalMinutesTextView;
    private TextView mResultWorkshopLearningLevelTextView;
    private TextView mTotalCostTextView;

    //Imagebutton
    private ImageButton mScheduleInfoBtn;
    private ImageButton mParticipantInfoBtn;
    private ImageButton mParticipantItemInfoBtn;

    private DatePickerDialog datePickerDialog;

    private int maxParticipants;
    //Total time variables;
    private int minuteT;
    private int roundT;
    private int totalTime;
    // Total Cost
    private Double totalCost;
    private Double totalPartCost;
    private int items;

    // Workshop name
    private String workshop;

    private Spinner mCategorieSpinner;
    private Spinner mWorkshopSpinner;
    private ArrayAdapter<CharSequence> categorieArrayAdapter;
    private ArrayAdapter<Product> workshopArrayAdapter;
    private List<Product> workshopDummylist;

    //cost
    private DecimalFormat df = new DecimalFormat("###.##");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);

        workshopDummylist = new ArrayList<>();

        //add dummylist
//        workshopDummylist.add(new Workshop(1, "Graffiti", new String[]{"Test", "Inhoud", "Info", "kosten"}, 55.55, "11-11-2021", 25, Category.DS));
//        workshopDummylist.add(new Workshop(1, "T-shirt Ontwerpen", new String[]{"Test", "Inhoud", "Info", "kosten"}, 55.55, "11-11-2021", 25, Category.BK));
//        workshopDummylist.add(new Workshop(1, "Result", new String[]{"Test", "Inhoud", "Info", "kosten"}, 55.55, "11-11-2021", 25, Category.MK));

        // Buttons
        mSendBn = findViewById(R.id.activity_workshop_booking_btn_book);
        mBackButton = findViewById(R.id.activity_workshop_booking_btn_back);


        // Setting up IDS
        mSendBn = findViewById(R.id.activity_cultureday_booking_btn_book);
        mBackButton = findViewById(R.id.activity_cultureday_booking_btn_back);
        mCategorieSpinner = findViewById(R.id.activity_cultureday_booking_spnr_category);
        mWorkshopSpinner = findViewById(R.id.activity_cultureday_booking_spnr_workshop);


        // Date
        mDateLayout = findViewById(R.id.activity_cultureday_booking_et_date);
        mDateEditText = findViewById(R.id.date_picker_edit_text);
        ImageButton datePickerButton = mDateLayout.findViewById(R.id.component_edittext_date_calendar_btn_calendar);
        datePickerDialog = new DatePickerDialog(this, CulturedayBookingActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        // Workshop Participants
        mParticipantsLayout= findViewById(R.id.activity_cultureday_booking_et_amount);
        mParticipantsEditText = findViewById(R.id.number_edit_text);
        mParticipantInfoBtn = mParticipantsLayout.findViewById(R.id.component_edittext_number_info_btn_info);
        // Rounds
        mRoundsEditText = (EditText) findViewById(R.id.activity_cultureday_booking_et_rounds);
        mResultWorkshopRoundsTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_rounds);
//        //Workshops per workshoprounds
        mWorkshopsPerRoundEditText = findViewById(R.id.activity_cultureday_booking_et_workshops);
        // minutes
        mMinuteEditText = (EditText) findViewById(R.id.activity_cultureday_booking_et_mins);
        mResultWorkshopMinutesPerRoundTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_mins);
        // Scheme
        mSchemeEditText = (EditText) findViewById(R.id.schedule_edit_text);
        mResultWorkshopSchemeTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_schedule);
        mScheduleInfoBtn = findViewById(R.id.component_edittext_plaintext_info_multiline_btn_info);
//        // Learning Level
        mLevelEditText = (EditText) findViewById(R.id.activity_cultureday_booking_et_level);
        mResultWorkshopLearningLevelTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_level);
        // Total cost
        mTotalCostTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_subtotal);
        // item participants
        mItemParticipantsLayout= findViewById(R.id.activity_cultureday_booking_et_special_workshops);
        mParticipantsItemEditText = (EditText) findViewById(R.id.number_edit_text);
        mParticipantItemInfoBtn = mItemParticipantsLayout.findViewById(R.id.component_edittext_number_info_btn_info);

        mResultWorkshopTotalMinutesTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_duration);

        //Total time
        this.minuteT = 0;
        this.roundT = 0 ;
        this.totalTime = 0;
        this.totalCost = 0.0;
        this.maxParticipants = 0;
        this.items = 0;
        categorieArrayAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categorieArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workshopArrayAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_spinner_item, workshopDummylist);

//=======
//        mResultWorkshopTotalMinutesTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_duration);
//
//        WorkshopArrayAdapter workshopArrayAdapter = new WorkshopArrayAdapter(this, workshopDummylist);
//        CategoryArrayAdapter categoryArrayAdapter = new CategoryArrayAdapter(this, getResources().getStringArray(R.array.category));
//>>>>>>> feat/ui-components

        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
        mCategorieSpinner.setAdapter(categorieArrayAdapter);

        mParticipantInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Aantal deelnemers mag niet meer dan 100 zijn.", Toast.LENGTH_SHORT).show();

            }
        });
        mScheduleInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Zet uw tijd schema hier.", Toast.LENGTH_SHORT).show();
            }
        });

        mParticipantItemInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Aantal items mag niet meer dan deelnemers zijn.", Toast.LENGTH_SHORT).show();
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

                if(!DateValidation.isValidDate(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mDateEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (DateValidation.isValidDate(editable.toString())){
                    mDateEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    dateValidation.mIsValid = true;
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

                if(!CultureDayParticipantsValidator.isValidMaxParticipant(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (CultureDayParticipantsValidator.isValidMaxParticipant(editable.toString())){
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    maxParticipants = Integer.valueOf(editable.toString());
                    cultureDayParticipantsValidator.mIsValid = true;
                } else {
                    maxParticipants = 0;
                }
            }
        });

        // workshop per Rounds
        mWorkshopsPerRoundEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!RoundsValidator.isValidWorkshopRounds(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (RoundsValidator.isValidWorkshopRounds(editable.toString())){
                    int rounds = Integer.parseInt(editable.toString());
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    // totalCosts
                    int roundss = Integer.valueOf(0 +mRoundsEditText.getText().toString());
                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());

                    Double itemsPrice = 7.50 * items;
                    workshopsPerRoundValidator.mIsValid = true;
                    mTotalCostTextView.setText("Subtotaal: €" + df.format(2.33*roundss*workshops*minute));

                } else {
                    mTotalCostTextView.setText("Subtotaal: ");


                }
            }
        });
        // rounds
        mRoundsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!RoundsValidator.isValidWorkshopRounds(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (RoundsValidator.isValidWorkshopRounds(editable.toString())){
                    int rounds = 0 +Integer.parseInt(editable.toString()) ;
                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: " + rounds);
                    roundT = rounds;
                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + minuteT*roundT + " minuten.");
                    // totalCosts
                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
                    Double itemsPrice = 7.50 * items;
                    roundsValidator.mIsValid = true;

                    mTotalCostTextView.setText("Subtotaal: €" + df.format(2.33*rounds*workshops*minute));
                } else {
                    mTotalCostTextView.setText("Subtotaal: ");

                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: ");
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

                if(!MinuteValidator.isValidMinute(charSequence.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_error);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (MinuteValidator.isValidMinute(editable.toString())){
                    int minutes = Integer.parseInt(editable.toString());
                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: " + minutes);
                    minuteT = minutes;
                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + minuteT*roundT + " minuten.");
                    // totalCosts
                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
                    Double itemsPrice = 7.50 * items;
                    minuteValidator.mIsValid = true;

                    mTotalCostTextView.setText("Subtotaal: €" + df.format(2.33*rounds*workshops*minute));
                } else {
                    mTotalCostTextView.setText("Subtotaal: ");
                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: ");
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
                if (!scheme.isEmpty()){
                    mSchemeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                } else {
                    mSchemeEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
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

        // item Participants
        mParticipantsItemEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(LOG_TAG, "onTextChanged: text changed");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(ParticipantsItemValidator.isValidParticipantsItemValidator(editable.toString(), maxParticipants)){
                    mParticipantsItemEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    // totalCosts
                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
                    items = Integer.valueOf(0 + editable.toString());
                    Double itemsPrice = 7.50 * items;

                    mTotalCostTextView.setText("Subtotaal: €" + df.format((2.33*rounds*workshops*minute) + itemsPrice));
                } else if (!ParticipantsItemValidator.isValidParticipantsItemValidator(editable.toString(), maxParticipants)) {
                    mParticipantsItemEditText.setBackgroundResource(R.drawable.edittext_error);
                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
                    items = 0;
                    Double itemsPrice = 7.50 * items;


                    mTotalCostTextView.setText("Subtotaal: €" + df.format((2.33*rounds*workshops*minute) + itemsPrice));

                } else {
                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
                    items = 0;
                    Double itemsPrice = 7.50 * items;


                    mTotalCostTextView.setText("Subtotaal: €" + df.format((2.33*rounds*workshops*minute) + itemsPrice));
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });

        mSendBn.setText("Boek nu");
        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Datum, deelnemers, rondes,workshopsperrondes, minuten, learning levels niet leeg, rest wel
                if(dateValidation.isValid() && cultureDayParticipantsValidator.isValid() && roundsValidator.isValid() && minuteValidator.isValid() && learningLevelValidator.isValid() && workshopsPerRoundValidator.isValid()){
                    Intent intent = new Intent(getApplicationContext(), ShoppingCartLayoutTestActivity.class);
                    StringBuilder stb = new StringBuilder();
                    stb.append(mDateEditText.getText());
                    stb.append(mParticipantsEditText.getText());
                    stb.append(mRoundsEditText.getText());
                    stb.append(mMinuteEditText.getText());
                    stb.append(mLevelEditText.getText());
                    stb.append(mWorkshopsPerRoundEditText.getText());
                    stb.append(mParticipantsItemEditText.getText());

                    intent.putExtra(Intent.EXTRA_TEXT, stb.toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Een van uw verplichte velden is nog leeg!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), CulturedayActivity.class);
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
