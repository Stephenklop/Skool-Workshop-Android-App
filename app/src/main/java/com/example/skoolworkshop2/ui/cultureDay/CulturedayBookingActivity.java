package com.example.skoolworkshop2.ui.cultureDay;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.CultureDayItem;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantsItemValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.logic.validation.WorkshopsPerRoundValidator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CulturedayBookingActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private final String LOG_TAG = getClass().getSimpleName();
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
    private RelativeLayout mItemsLinearLayout;
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

    private HorizontalScrollView mHzItemsView;
    private DatePickerDialog datePickerDialog;

    private int maxParticipants;
    //Total time variables;
    private int minuteT;
    private int roundT;
    private int totalTime;
    private int times = 0;
    // total items selected
    private int totalItemsSelected;
    // Total Cost
    private Double totalCost;
    private Double totalPartCost;
    // CultureDay
    private CultureDayItem cultureDay;
    private int items;

    private Spinner mCategorieSpinner;
    private Spinner mWorkshopSpinner;
    private ArrayAdapter<CharSequence> categorieArrayAdapter;
    private ArrayAdapter<WorkshopItem> workshopArrayAdapter;
    private List<Product> workshopDummylist;
    private List<String> workshopNames;

    //cost
    private DecimalFormat df = new DecimalFormat("###.##");

    // Spinner
    private String item;
    private ArrayList<String> names = new ArrayList<>();

    // Radiogroup
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private boolean name;

    //Imagebutton
    private ImageButton mScheduleInfoBtn;
    private ImageButton mParticipantInfoBtn;
    private ImageButton mParticipantItemInfoBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);



        workshopDummylist = new ArrayList<>();
//        selectedCategories = new ArrayList<>();

        //add dummylist
        //workshopDummylist.add(new Product(1, "test", "test", "booking", "test desc", "status", "hoi", "buildup", "desc", "cost info", "srcImage", "ImageName", "video"));

        // Variabelen voor workshopnamen voor spinner
        workshopNames = new ArrayList<>();
        workshopNames.add(0, "Kies een workshop");
        for (int i = 0; i < workshopDummylist.size(); i++){
            workshopNames.add(workshopDummylist.get(i).getName());
        }
        Log.d(LOG_TAG, "onCreate: workshopnames" + workshopNames);

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
//        datePickerDialog = new DatePickerDialog(this, CulturedayBookingActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
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
        //Workshops per workshoprounds
        mWorkshopsPerRoundEditText = findViewById(R.id.activity_cultureday_booking_et_workshops);
        // minutes
        mMinuteEditText = (EditText) findViewById(R.id.activity_cultureday_booking_et_mins);
        mResultWorkshopMinutesPerRoundTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_mins);
        // Scheme
        mSchemeEditText = (EditText) findViewById(R.id.schedule_edit_text);
        mResultWorkshopSchemeTextView = (TextView) findViewById(R.id.activity_cultureday_booking_tv_schedule);
        mScheduleInfoBtn = findViewById(R.id.component_edittext_plaintext_info_multiline_btn_info);
        // Learning Level
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
//        this.minuteT = 0;
//        this.roundT = 0 ;
//        this.totalTime = 0;
//        this.totalCost = 0.0;
//        this.maxParticipants = 0;
//        this.items = 0;
//
//        // Everything for spinner
//        categorieArrayAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
//        categorieArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        workshopArrayAdapter = new ArrayAdapter<WorkshopItem>(this, android.R.layout.simple_spinner_item, workshopDummylist);
//
//
//        // Workshop spinner
//        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, workshopNames);
//        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//        mWorkshopSpinner.setSelection(mWorkshopSpinner.getSelectedItemPosition(), false);
//        mWorkshopSpinner.setOnItemSelectedListener(this);
//        // Workshop categorie spinner
//        mCategorieSpinner.setAdapter(categorieArrayAdapter);
//        mCategorieSpinner.setSelection(mCategorieSpinner.getSelectedItemPosition(), false);
//        mCategorieSpinner.setOnItemSelectedListener(this);
//        // Radiogroup
//        mRadioGroup = findViewById(R.id.activity_cultureday_booking_rg_workshops);
//
//        mParticipantInfoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Aantal deelnemers mag niet meer dan 100 zijn.", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        mScheduleInfoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Zet uw tijd schema hier.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mParticipantItemInfoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Aantal items mag niet meer dan deelnemers zijn.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        //Use validator
//        // Date Validator
//        mDateEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                mDateEditText.setBackgroundResource(R.drawable.edittext_focused);
//
//                if(!DateValidation.isValidDate(charSequence.toString())){
//                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
//                    mDateEditText.setBackgroundResource(R.drawable.edittext_error);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (DateValidation.isValidDate(editable.toString())){
//                    mDateEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//
//                    dateValidation.mIsValid = true;
//                }
//            }
//        });
//
//
//        // Participants
//        mParticipantsEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);
//
//                if(!CultureDayParticipantsValidator.isValidMaxParticipant(charSequence.toString())){
//                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
//                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (CultureDayParticipantsValidator.isValidMaxParticipant(editable.toString())){
//                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                    maxParticipants = Integer.valueOf(editable.toString());
//                    cultureDayParticipantsValidator.mIsValid = true;
//                } else {
//                    maxParticipants = 0;
//                }
//            }
//        });
//
//        // workshop per Rounds
//        mWorkshopsPerRoundEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);
//
//                if(!RoundsValidator.isValidWorkshopRounds(charSequence.toString())){
//                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
//                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                if (RoundsValidator.isValidWorkshopRounds(editable.toString())){
//                    int rounds = Integer.parseInt(editable.toString());
//                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                    // totalCosts
//                    int roundss = Integer.valueOf(0 +mRoundsEditText.getText().toString());
//                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
//                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
//
//                    Double itemsPrice = 7.50 * items;
//                    workshopsPerRoundValidator.mIsValid = true;
//                    mTotalCostTextView.setText("Subtotaal: €" + df.format(2.33*roundss*workshops*minute));
//
//                } else {
//                    mTotalCostTextView.setText("Subtotaal: ");
//
//
//                }
//            }
//        });
//        // rounds
//        mRoundsEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                mRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
//
//                if(!RoundsValidator.isValidWorkshopRounds(charSequence.toString())){
//                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
//                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                if (RoundsValidator.isValidWorkshopRounds(editable.toString())){
//                    int rounds = 0 +Integer.parseInt(editable.toString()) ;
//                    mRoundsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: " + rounds);
//                    roundT = rounds;
//                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + minuteT*roundT + " minuten.");
//                    // totalCosts
//                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
//                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
//                    Double itemsPrice = 7.50 * items;
//                    roundsValidator.mIsValid = true;
//
//                    mTotalCostTextView.setText("Subtotaal: €" + df.format(2.33*rounds*workshops*minute));
//                } else {
//                    mTotalCostTextView.setText("Subtotaal: ");
//
//                    mResultWorkshopRoundsTextView.setText("Aantal workshoprondes: ");
//                }
//            }
//        });
//        //Total time
//        mResultWorkshopTotalMinutesTextView.setText("Totale duur: ");
//        // Minutes
//        mMinuteEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                mMinuteEditText.setBackgroundResource(R.drawable.edittext_focused);
//
//                if(!MinuteValidator.isValidMinute(charSequence.toString())){
//                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
//                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_error);
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (MinuteValidator.isValidMinute(editable.toString())){
//                    int minutes = Integer.parseInt(editable.toString());
//                    mMinuteEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: " + minutes);
//                    minuteT = minutes;
//                    mResultWorkshopTotalMinutesTextView.setText("Totale duur: " + minuteT*roundT + " minuten.");
//                    // totalCosts
//                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
//                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
//                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
//                    Double itemsPrice = 7.50 * items;
//                    minuteValidator.mIsValid = true;
//
//                    mTotalCostTextView.setText("Subtotaal: €" + df.format(2.33*rounds*workshops*minute));
//                } else {
//                    mTotalCostTextView.setText("Subtotaal: ");
//                    mResultWorkshopMinutesPerRoundTextView.setText("Aantal minuten per workshopronde: ");
//                }
//            }
//        });
//
//        //Schedule scheme
//        mSchemeEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mSchemeEditText.setBackgroundResource(R.drawable.edittext_focused);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String scheme = s.toString();
//                if (!scheme.isEmpty()){
//                    mSchemeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                } else {
//                    mSchemeEditText.setBackgroundResource(R.drawable.edittext_focused);
//                }
//                mResultWorkshopSchemeTextView.setText("Tijdschema: " + scheme);
//            }
//        });
//
//        //level
//        mLevelEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mLevelEditText.setBackgroundResource(R.drawable.edittext_focused);
//                if(!learningLevelValidator.isValidLearningLevels(s.toString())){
//                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
//                    mLevelEditText.setBackgroundResource(R.drawable.edittext_error);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (learningLevelValidator.isValidLearningLevels(s.toString())){
//                    mLevelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                    mResultWorkshopLearningLevelTextView.setText("Leerniveau: " + s.toString());
//                    learningLevelValidator.mIsValid = true;
//                } else {
//                    mResultWorkshopLearningLevelTextView.setText("Leerniveau: ");
//                }
//            }
//        });
//
//        // item Participants
//        mParticipantsItemEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d(LOG_TAG, "onTextChanged: text changed");
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(ParticipantsItemValidator.isValidParticipantsItemValidator(editable.toString(), maxParticipants)){
//                    mParticipantsItemEditText.setBackgroundResource(R.drawable.edittext_confirmed);
//                    // totalCosts
//                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
//                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
//                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
//                    items = Integer.valueOf(0 + editable.toString());
//                    Double itemsPrice = 7.50 * items;
//
//                    mTotalCostTextView.setText("Subtotaal: €" + df.format((2.33*rounds*workshops*minute) + itemsPrice));
//                } else if (!ParticipantsItemValidator.isValidParticipantsItemValidator(editable.toString(), maxParticipants)) {
//                    mParticipantsItemEditText.setBackgroundResource(R.drawable.edittext_error);
//                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
//                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
//                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
//                    items = 0;
//                    Double itemsPrice = 7.50 * items;
//
//
//                    mTotalCostTextView.setText("Subtotaal: €" + df.format((2.33*rounds*workshops*minute) + itemsPrice));
//
//                } else {
//                    int rounds = Integer.valueOf(0 + mRoundsEditText.getText().toString());
//                    int workshops = Integer.valueOf(0 +mWorkshopsPerRoundEditText.getText().toString());
//                    int minute = Integer.valueOf(0 +mMinuteEditText.getText().toString());
//                    items = 0;
//                    Double itemsPrice = 7.50 * items;
//
//
//                    mTotalCostTextView.setText("Subtotaal: €" + df.format((2.33*rounds*workshops*minute) + itemsPrice));
//                }
//            }
//        });
//
//        mBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(backIntent);
//            }
//        });
//
//        mSendBn.setText("Boek nu");
//        mSendBn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Datum, deelnemers, rondes,workshopsperrondes, minuten, learning levels niet leeg, rest wel
//                if(dateValidation.isValid() && cultureDayParticipantsValidator.isValid() && roundsValidator.isValid() && minuteValidator.isValid() && learningLevelValidator.isValid() && workshopsPerRoundValidator.isValid()){
//                    Intent intent = new Intent(getApplicationContext(), ShoppingCartLayoutTestActivity.class);
//                    StringBuilder stb = new StringBuilder();
//                    stb.append(mDateEditText.getText());
//                    stb.append(mParticipantsEditText.getText());
//                    stb.append(mRoundsEditText.getText());
//                    stb.append(mMinuteEditText.getText());
//                    stb.append(mLevelEditText.getText());
//                    stb.append(mWorkshopsPerRoundEditText.getText());
//                    stb.append(mParticipantsItemEditText.getText());
//
//                    intent.putExtra(Intent.EXTRA_TEXT, stb.toString());
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Een van uw verplichte velden is nog leeg!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//    }
//
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        if(dayOfMonth < 10 && month < 10){
//            mDateEditText.setText("0" + dayOfMonth + "/0" + month + "/" + year);
//        } else if (dayOfMonth < 10){
//            mDateEditText.setText("0" + dayOfMonth + "/" + month + "/" + year);
//        } else if (month < 10){
//            mDateEditText.setText(dayOfMonth + "/0" + month + "/" + year);
//        } else {
//            mDateEditText.setText(dayOfMonth + "/" + month + "/" + year);
//        }
//        datePickerDialog.cancel();
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        // Voor categorySpinner
//        switch (adapterView.getId()) {
//            case R.id.activity_cultureday_booking_spnr_category:
//                Log.d(LOG_TAG, "onItemSelected: Selected category spinner");
//                ArrayList<Workshop> categoryWorkshops = new ArrayList<>();
//                selectedCategories = new ArrayList<>();
//                item = mCategorieSpinner.getSelectedItem().toString();
//                switch (item) {
//                    case "Kies een categorie":
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                    case "Meest gekozen":
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, workshopNames);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                    case "Beeldende Kunst":
//                        selectedCategories.add(workshopNames.get(0));
//
//                        for (Workshop workshop : workshopDummylist) {
//                            if (workshop.getCategory().label.equals("Beeldende Kunst")) {
//                                selectedCategories.add(workshop.getName());
//                                Log.d(LOG_TAG, "onItemSelected: workshop: " + workshop);
//                            }
//                            }
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selectedCategories);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                    case "Dans":
//                        selectedCategories.add(workshopNames.get(0));
//
//                        for (Workshop workshop : workshopDummylist) {
//                            if (workshop.getCategory().label.equals("Dans")) {
//                                selectedCategories.add(workshop.getName());
//                                Log.d(LOG_TAG, "onItemSelected: workshop: " + workshop);
//                            }
//                        }
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selectedCategories);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                    case "Media":
//                        selectedCategories.add(workshopNames.get(0));
//
//                        for (Workshop workshop : workshopDummylist) {
//                            if (workshop.getCategory().label.equals("Media")) {
//                                selectedCategories.add(workshop.getName());
//                                Log.d(LOG_TAG, "onItemSelected: workshop: " + workshop);
//                            }
//                        }
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selectedCategories);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                    case "Muziek":
//                        selectedCategories.add(workshopNames.get(0));
//
//                        for (Workshop workshop : workshopDummylist) {
//                            if (workshop.getCategory().label.equals("Muziek")) {
//                                selectedCategories.add(workshop.getName());
//                                Log.d(LOG_TAG, "onItemSelected: workshop: " + workshop);
//                            }
//                        }
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selectedCategories);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                    case "Sport":
//                        selectedCategories.add(workshopNames.get(0));
//
//                        for (Workshop workshop : workshopDummylist) {
//                            if (workshop.getCategory().label.equals("Sport")) {
//                                selectedCategories.add(workshop.getName());
//                                Log.d(LOG_TAG, "onItemSelected: workshop: " + workshop);
//                            }
//                        }
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selectedCategories);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                    case "Theater":
//                        selectedCategories.add(workshopNames.get(0));
//
//                        for (Workshop workshop : workshopDummylist) {
//                            if (workshop.getCategory().label.equals("Theater")) {
//                                selectedCategories.add(workshop.getName());
//                                Log.d(LOG_TAG, "onItemSelected: workshop: " + workshop);
//                            }
//                        }
//                        workshopArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selectedCategories);
//                        mWorkshopSpinner.setAdapter(workshopArrayAdapter);
//                        break;
//                }
//                break;
//            case R.id.activity_cultureday_booking_spnr_workshop:
//                if (totalItemsSelected >= 3) {
//                    Toast.makeText(getApplicationContext(), "Error: you already have 3 workshops", Toast.LENGTH_SHORT).show();
//                    break;
//                } else {
//                    name = false;
//                    Log.d(LOG_TAG, "onItemSelected: Selected workshop spinner on position: " + mWorkshopSpinner.getSelectedItem());
//                    String workshopName = mWorkshopSpinner.getSelectedItem().toString();
//                    Log.d(LOG_TAG, "onItemSelected: workshopName: " + workshopName);
//                    // Gaat door lijst van dummyworkshop
//                    for (Workshop workshop : workshopDummylist) {
//                        // Kijk of workshop geen default is en geselecteerde workshop uit lijst pakken
//                        if (!workshop.getName().equals("Default") && workshop.getName().equals(workshopName)) {
//                            // Kijkt of naam zelfde is als gegeven workshop
//                            if (names.size() != 0) {
//                                for (int d = 0; d < names.size(); d++) {
//                                    Log.d(LOG_TAG, "onItemSelected: name: " + names);
//                                    if (names.get(d).equals(workshopName)) {
//                                        Toast.makeText(getApplicationContext(), "Error: you already selected this one", Toast.LENGTH_SHORT).show();
//                                        Log.d(LOG_TAG, "onItemSelected: Workshop is already in here");
//                                        name = true;
//                                    }
//                                }
//                                    if (!name){
//                                        // Voor validatie of workshop niet al toegevoegd is
//                                        names.add(workshopName);
//                                        // Opzetten van radiogroup en button
//                                        mRadioButton = new RadioButton(getApplicationContext());
//                                        mRadioButton.setText(workshopName);
//                                        mRadioButton.setBackgroundResource(R.drawable.btn_orange);
//                                        mRadioButton.setButtonDrawable(null);
//                                        mRadioButton.setTag(workshopName);
//                                        mRadioGroup.addView(mRadioButton);
//
//                                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                            @Override
//                                            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                                                // kijken of naam overeenkomt met button.gettext
//
//                                                i = times + mRadioGroup.getCheckedRadioButtonId();
//                                                Log.d(LOG_TAG, "onCheckedChanged: id: " + i);
//                                                // remove van lijst en group
//                                                totalItemsSelected--;
//
//                                                RadioButton button = findViewById(mRadioGroup.getCheckedRadioButtonId());
//                                                mRadioGroup.removeView(button);
//                                                String text = button.getText().toString();
//                                                for (int d = 0; d < names.size(); d++) {
//                                                    if (names.get(d).equals(text)) {
//                                                        names.remove(d);
//                                                    }
//                                                }
//                                                Log.d(LOG_TAG, "onCheckedChanged: names: " + names);
//                                                times++;
//                                            }
//                                        });
//
//                                        // Er mogen maar 3 items toegevoegd worden.
//                                        totalItemsSelected++;
//                                        break;
//                                    }
//                            } else {
//                                // Voor validatie of workshop niet al toegevoegd is
//                                names.add(workshopName);
//                                // Opzetten van radiogroup en button
//                                mRadioButton = new RadioButton(getApplicationContext());
//                                mRadioButton.setText(workshopName);
//                                mRadioButton.setBackgroundResource(R.drawable.btn_orange);
//                                mRadioButton.setButtonDrawable(null);
//                                mRadioButton.setTag(workshopName);
//                                mRadioGroup.addView(mRadioButton);
//
//                                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                                        // kijken of naam overeenkomt met button.gettext
//
//                                        i = times + mRadioGroup.getCheckedRadioButtonId();
//                                        Log.d(LOG_TAG, "onCheckedChanged: id: " + i);
//                                        // remove van lijst en group
//                                        totalItemsSelected--;
//
//                                        RadioButton button = findViewById(mRadioGroup.getCheckedRadioButtonId());
//                                        mRadioGroup.removeView(button);
//                                        String text = button.getText().toString();
//                                        for (int d = 0; d < names.size(); d++) {
//                                            if (names.get(d).equals(text)) {
//                                                names.remove(d);
//                                            }
//                                        }
//                                        Log.d(LOG_TAG, "onCheckedChanged: names: " + names);
//                                        times++;
//                                    }
//                                });
//
//                                // Er mogen maar 3 items toegevoegd worden.
//                                totalItemsSelected++;
//                                break;
//                            }
//                        }
//                    }
//                }
//                }
//        }
//
//
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
//
//
//    @Override
//    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {
//        if (byUser){
//            Toast.makeText(getApplicationContext()
//                    , "Dismissed", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
