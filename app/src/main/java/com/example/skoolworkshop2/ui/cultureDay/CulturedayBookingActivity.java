package com.example.skoolworkshop2.ui.cultureDay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.CultureDayItem;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.converters.DateConverter;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantsItemValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.logic.validation.WorkshopsPerRoundValidator;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.example.skoolworkshop2.ui.cultureDay.adapters.CategoryArrayAdapter;
import com.example.skoolworkshop2.ui.cultureDay.adapters.WorkshopArrayAdapter;
import com.example.skoolworkshop2.ui.shoppingCart.ShoppingCartActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CulturedayBookingActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
    private String LOG_TAG = getClass().getSimpleName();
    private Product mCultureDay;
    private CultureDayItem mCultureDayItem;
    private List<Integer> mSelectedWorkshops;
    private List<Product> mWorkshops;
    private List<String> mWorkshopCategories;
    private List<String> mWorkshopNames;
    private ImageButton mBackButton;
    private ImageView mBanner;
    private TextView mTitle;
    private DatePickerDialog mDatePickerDialog;
    private EditText mDateEditText;
    private EditText mParticipantsEditText;
    private EditText mWorkshopRoundsEditText;
    private EditText mWorkshopsPerRoundEditText;
    private EditText mDurationPerRoundEditText;
    private Spinner mCategorySpinner;
    private CategoryArrayAdapter mCategoryArrayAdapter;
    private Spinner mWorkshopSpinner;
    private WorkshopArrayAdapter mWorkshopArrayAdapter;
    private LinearLayout mWorkshopsLinearLayout;
    private EditText mTimeScheduleEditText;
    private EditText mParticipantsGraffitiThsirtEditText;
    private EditText mLearningLevelEditText;

    private ImageButton mParticipantsInfoBtn;
    private ImageButton mTimeScheduleInfoBtn;
    private ImageButton mParticipantsGraffitiThsirtInfoBnt;

    private TextView mOverviewWorkshopRounds;
    private TextView mOverviewDurationPerRound;
    private TextView mOverviewTotalDuration;
    private TextView mOverviewTimeSchedule;
    private TextView mOverviewLearningLevel;
    private TextView mOverviewTotalCost;
    private Button mOrderButton;

    private CultureDayParticipantsValidator mCultureDayParticipantsValidator = new CultureDayParticipantsValidator();
    private RoundsValidator mRoundsValidator = new RoundsValidator();
    private WorkshopsPerRoundValidator mWorkshopsPerRoundValidator = new WorkshopsPerRoundValidator();
    private MinuteValidator mMinuteValidator;
    private LearningLevelValidator mLearningLevelValidator = new LearningLevelValidator();
    private DateValidation dateValidation = new DateValidation();
    private ParticipantsItemValidator participantsItemValidator = new ParticipantsItemValidator();

    private TextView mErrTv;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        mMinuteValidator = new MinuteValidator();

        TextView mPriceBn = findViewById(R.id.activity_cultureday_booking_btn_price);
        TextView mParticipantsBn = findViewById(R.id.activity_cultureday_booking_btn_participant);
        TextView mWorkshopsBn = findViewById(R.id.activity_cultureday_booking_btn_workshop);
        TextView mRoundsBn = findViewById(R.id.activity_cultureday_booking_btn_round);

        mPriceBn.setText("€1674,-");
        mParticipantsBn.setText("100 Deelnemers");
        mWorkshopsBn.setText("4 Workshops");
        mRoundsBn.setText("3 Rondes");


        // Initialize attributes
        initializeAttributes();

        mBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CulturedayActivity.class);
            startActivity(intent);
        });

        Glide.with(getBaseContext()).load(mCultureDay.getSourceImage()).into(mBanner);

        mTitle.setText(mCultureDay.getName());

        // TODO: Add date validation (to ensure the date is in the future)
        mDateEditText.setOnClickListener(v -> mDatePickerDialog.show());
        mDateEditText.setFocusable(false);
        mDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mDateEditText.equals("")) {
                    if (dateValidation.isValidDate(s.toString())) {
                        mDateEditText.setBackgroundResource(R.drawable.edittext_default);
                        dateValidation.setmIsValid(true);

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mDateEditText.setBackgroundResource(R.drawable.edittext_error);
                        dateValidation.setmIsValid(false);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setDate(s.toString());
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

        mParticipantsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mParticipantsEditText.equals("")) {
                    if (mCultureDayParticipantsValidator.isValidMaxParticipant(s.toString())) {
                        updateOrderOverview();
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        mCultureDayParticipantsValidator.setmIsValid(true);
                    } else if (!mCultureDayParticipantsValidator.isValidMaxParticipant(s.toString())) {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                        mCultureDayParticipantsValidator.setmIsValid(false);
                    } else {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setParticipants((s.toString().equals("")) ? 0 : Integer.parseInt(s.toString()));
            }
        });
        mParticipantsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(mCultureDayParticipantsValidator.isValid()) {
                        mParticipantsEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);
                }

            }
        });

        mWorkshopRoundsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
                if(!mWorkshopRoundsEditText.equals("")) {

                    if (mRoundsValidator.isValidWorkshopRounds(s.toString())) {
                        mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        mRoundsValidator.setmIsValid(true);
                    } else if (!mRoundsValidator.isValidWorkshopRounds(s.toString())){
                        mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
                        mRoundsValidator.setmIsValid(false);
                    } else{
                        mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!mWorkshopRoundsEditText.equals("") && mWorkshopRoundsEditText.getText().length() > 0) {
                    mCultureDayItem.setRounds(Integer.parseInt(s.toString()));

                }
                updateOrderOverview();


            }
        });
        mWorkshopRoundsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(mRoundsValidator.isValid()) {
                        mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_focused);
                }

            }
        });


        mWorkshopsPerRoundEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mWorkshopsPerRoundEditText.equals("")) {

                    if (!RoundsValidator.isValidWorkshopRounds(s.toString())) {
                        mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
                        mWorkshopsPerRoundValidator.setmIsValid(false);
                    } else if (RoundsValidator.isValidWorkshopRounds(s.toString())) {
                        mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        mWorkshopsPerRoundValidator.setmIsValid(true);
                    } else{
                        mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!mWorkshopsPerRoundEditText.getText().toString().equals("") && mWorkshopRoundsEditText.getText().toString().length() > 0) {
                    mCultureDayItem.setWorkshopPerWorkshopRound(Integer.parseInt(s.toString()));
                }
                updateOrderOverview();
            }
        });
        mWorkshopsPerRoundEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(mWorkshopsPerRoundValidator.isValid()) {
                        mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else {
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);
                }

            }
        });

        mDurationPerRoundEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!mDurationPerRoundEditText.equals("")) {
                    if (mDurationPerRoundEditText.getText().length() > 0) {
                        mCultureDayItem.setRoundDuration(Integer.parseInt(s.toString()));
                    }

                    if (mCultureDayItem.getPrice() > 0) {
                        mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        mMinuteValidator.setmIsValid(true);
                    } else {
                        mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!mDurationPerRoundEditText.equals("") && mDurationPerRoundEditText.getText().length() > 0) {
                    mCultureDayItem.setRoundDuration(Integer.parseInt(s.toString()));
                }
                updateOrderOverview();
            }
        });
        mDurationPerRoundEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(mMinuteValidator.isValid()) {
                        mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);
                }

            }
        });

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mWorkshopNames = loadWorkshopNames(mWorkshopCategories.get(position));
                    mWorkshopSpinner.setEnabled(false);
                } else {
                    mWorkshopNames = loadWorkshopNames(mWorkshopCategories.get(position - 1));
                    mWorkshopSpinner.setEnabled(true);
                }

                mWorkshopArrayAdapter.updateData(mWorkshopNames);
                mWorkshopArrayAdapter.refreshList();
                mWorkshopSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCategoryArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        mCategorySpinner.setAdapter(mCategoryArrayAdapter);

        mWorkshopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    int productId = LocalDb.getDatabase(getBaseContext()).getProductDAO().getProductIdByName(selectedItem);

                    LinearLayout button = (LinearLayout) LayoutInflater.from(getBaseContext())
                            .inflate(R.layout.component_button_medium_extendable_delete, mWorkshopsLinearLayout, false);

                    TextView buttonLabel = button.findViewById(R.id.component_button_medium_extendable_delete_tv_label);
                    ImageButton xButton = button.findViewById(R.id.component_button_medium_extendable_delete_btn_x);
                    buttonLabel.setText(selectedItem);

                    View.OnClickListener removeListener = v -> {
                        button.animate().alpha(0).setDuration(250).withEndAction(() -> {
                            mWorkshopsLinearLayout.removeView(button);
                        }).start();
                        mSelectedWorkshops.remove((Object) productId);
                    };

                    button.setOnClickListener(removeListener);
                    xButton.setOnClickListener(removeListener);

                    mWorkshopsLinearLayout.addView(button);
                    mSelectedWorkshops.add(productId);

                    mCategorySpinner.setSelection(0);
                    mWorkshopSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mWorkshopArrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        mWorkshopSpinner.setAdapter(mWorkshopArrayAdapter);

        mTimeScheduleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTimeScheduleEditText.setBackgroundResource(R.drawable.edittext_focused);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    mTimeScheduleEditText.setBackgroundResource(R.drawable.edittext_focused);
                } else {
                    mTimeScheduleEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mCultureDayItem.setTimeSchedule(s.toString());
                    System.out.println("TIJDSCHEMA GEWIJZIGD: " + s.toString());
                }

                updateOrderOverview();
            }
        });
        mTimeScheduleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    mTimeScheduleEditText.setBackgroundResource(R.drawable.edittext_default);

                } else{
                    mTimeScheduleEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mParticipantsGraffitiThsirtEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mParticipantsGraffitiThsirtEditText.equals("")) {
                    if (participantsItemValidator.isValidParticipantsItemValidator(s.toString(), 25)) {
                        mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        participantsItemValidator.mIsValid = true;
                        if(!mParticipantsGraffitiThsirtEditText.equals("") && mParticipantsGraffitiThsirtEditText.getText().length() > 0) {

                            mCultureDayItem.setAmountOfParticipantsGraffitiTshirt(Integer.parseInt(s.toString()));
                        }
                        updateOrderOverview();
                    } else if (!participantsItemValidator.isValidParticipantsItemValidator(s.toString(), 25)) {
                        mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_error);
                        participantsItemValidator.mIsValid = false;

                    } else {
                        mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_focused);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mParticipantsGraffitiThsirtEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(participantsItemValidator.isValid()) {
                        mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mLearningLevelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mLearningLevelValidator.isValidLearningLevels(s.toString())){
                    mLearningLevelEditText.setBackgroundResource(R.drawable.edittext_error);
                    mLearningLevelValidator.mIsValid = false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mLearningLevelValidator.isValidLearningLevels(s.toString())){
                    mLearningLevelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mCultureDayItem.setLearningLevel(s.toString());
                    mLearningLevelValidator.mIsValid = true;
                }

                updateOrderOverview();
            }
        });
        mLearningLevelEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(mLearningLevelValidator.isValid()) {
                        mLearningLevelEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mLearningLevelEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mOrderButton.setText("Boek nu");
        mOrderButton.setOnClickListener(v -> {
            if (validate()) {
                mErrTv.setVisibility(View.GONE);
                DatePicker datePicker = mDatePickerDialog.getDatePicker();

                ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                        mCultureDay.getProductId(),
                        false,
                        mCultureDayItem.getDate(),
                        mCultureDayItem.getRounds(),
                        mCultureDayItem.getWorkshopPerWorkshopRound(),
                        mCultureDayItem.getRoundDuration(),
                        mCultureDayItem.getTimeSchedule(),
                        mCultureDayItem.getParticipants(),
                        mCultureDayItem.getAmountOfParticipantsGraffitiTshirt(),
                        mCultureDayItem.getLearningLevel(),
                        DateConverter.datePickerConverter(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 12, 0, 0, "00:00"),
                        DateConverter.datePickerConverter(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 23, 59, 59, "00:00"),
                        mCultureDayItem.getPrice()
                );

                shoppingCartItem.setProducts(mSelectedWorkshops);

                System.out.println("BOOKED CULTURE DAY: " + shoppingCartItem);
                System.out.println("WORKSHOPS: " + mSelectedWorkshops);

                LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().insertItemInShoppingCart(shoppingCartItem);

                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
            } else {
                mErrTv.setVisibility(View.VISIBLE);
                mErrTv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tv_err_translate_anim));
            }
        });

        mParticipantsInfoBtn.setOnClickListener(v -> {
            String header = "Totaal aantal deelnemers";
            String content = "Maximaal 100 deelnemers aan een cultuurdag";
            new RoundedDialog(CulturedayBookingActivity.this, header, content);
        });

        mTimeScheduleInfoBtn.setOnClickListener(v -> {
            String header = "Tijdschema";
            String content = "Geef hier op hoe jullie het tijdschema willen hebben (aantal rondes met eventueel pauzes)";
            new RoundedDialog(CulturedayBookingActivity.this, header, content);
        });

        mParticipantsGraffitiThsirtInfoBnt.setOnClickListener(v -> {
            String header = "Deelnemers Graffiti en T-Shirt ontwerpen (+€7,50)";
            String content = "Indien je de workshop Graffiti of T-shirt ontwerpen afneemt bereken wij o.b.v. het aantal deelnemers de materiaalkosten.";
            new RoundedDialog(CulturedayBookingActivity.this, header, content);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initializeAttributes() {
        // Main view
        mCultureDay = (Product) getIntent().getSerializableExtra("cultureDay");
        mCultureDayItem = new CultureDayItem(mCultureDay);
        mSelectedWorkshops = new ArrayList<>();
        mWorkshops = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByType("Workshop");
        mWorkshopCategories = loadWorkshopCategories();
        mWorkshopNames = loadWorkshopNames("");
        mBackButton = findViewById(R.id.activity_cultureday_booking_btn_back);
        mBanner = findViewById(R.id.activity_cultureday_booking_img_banner);
        mTitle = findViewById(R.id.activity_cultureday_booking_tv_title);
        mDatePickerDialog = new DatePickerDialog(this, R.style.Theme_SkoolWorkshop2_DatePicker, CulturedayBookingActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        mDateEditText = findViewById(R.id.date_picker_edit_text);
        mParticipantsEditText = findViewById(R.id.activity_cultureday_booking_et_amount).findViewById(R.id.number_edit_text);
        mParticipantsInfoBtn = findViewById(R.id.activity_cultureday_booking_et_amount).findViewById(R.id.component_edittext_number_info_btn_info);
        mWorkshopRoundsEditText = findViewById(R.id.activity_cultureday_booking_et_rounds);
        mWorkshopsPerRoundEditText = findViewById(R.id.activity_cultureday_booking_et_workshops);
        mDurationPerRoundEditText = findViewById(R.id.activity_cultureday_booking_et_mins);
        mCategorySpinner = findViewById(R.id.activity_cultureday_booking_spnr_category);
        mCategoryArrayAdapter = new CategoryArrayAdapter(this, R.layout.item_spinner_dropdown, mWorkshopCategories); //new ArrayAdapter<>(this, R.layout.item_spinner_dropdown, mWorkshopCategories);
        mWorkshopSpinner = findViewById(R.id.activity_cultureday_booking_spnr_workshop);
        mWorkshopArrayAdapter = new WorkshopArrayAdapter(this, R.layout.item_spinner_dropdown, mWorkshopNames);
        mWorkshopsLinearLayout = findViewById(R.id.activity_cultureday_booking_workshops);
        mTimeScheduleEditText = findViewById(R.id.schedule_edit_text);
        mTimeScheduleInfoBtn = findViewById(R.id.activity_cultureday_booking_et_schedule).findViewById(R.id.component_edittext_plaintext_info_multiline_btn_info);
        mParticipantsGraffitiThsirtEditText = findViewById(R.id.activity_cultureday_booking_et_special_workshops).findViewById(R.id.number_edit_text);
        mParticipantsGraffitiThsirtInfoBnt = findViewById(R.id.activity_cultureday_booking_et_special_workshops).findViewById(R.id.component_edittext_number_info_btn_info);
        mLearningLevelEditText = findViewById(R.id.activity_cultureday_booking_et_level);

        // Overview
        mOverviewWorkshopRounds = findViewById(R.id.activity_cultureday_booking_tv_rounds);
        mOverviewDurationPerRound = findViewById(R.id.activity_cultureday_booking_tv_mins);
        mOverviewTotalDuration = findViewById(R.id.activity_cultureday_booking_tv_duration);
        mOverviewTimeSchedule = findViewById(R.id.activity_cultureday_booking_tv_schedule);
        mOverviewLearningLevel = findViewById(R.id.activity_cultureday_booking_tv_level);
        mOverviewTotalCost = findViewById(R.id.activity_cultureday_booking_tv_subtotal);
        mOrderButton = findViewById(R.id.activity_cultureday_booking_btn_book);

        // Validation
        mCultureDayParticipantsValidator = new CultureDayParticipantsValidator();
        mWorkshopsPerRoundValidator = new WorkshopsPerRoundValidator();
        mMinuteValidator = new MinuteValidator();
        mLearningLevelValidator = new LearningLevelValidator();

        // Error
        mErrTv = findViewById(R.id.activity_cultureday_booking_tv_err);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        if(dayOfMonth < 10 && month < 10){
            mDateEditText.setText("0" + dayOfMonth + "/0" + month + "/" + year);
        } else if (dayOfMonth < 10){
            mDateEditText.setText("0" + dayOfMonth + "/" + month + "/" + year);
        } else if (month < 10){
            mDateEditText.setText(dayOfMonth + "/0" + month + "/" + year);
        } else {
            mDateEditText.setText(dayOfMonth + "/" + month + "/" + year);
        }
        mDatePickerDialog.cancel();

    }

    private List<String> loadWorkshopCategories() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < mWorkshops.size(); i++) {
            String category = mWorkshops.get(i).getCategory();

            // Avoid duplicate categories showing up
            if (result.stream().noneMatch(o -> o.equals(category)) && !category.isEmpty()) {
                result.add(category);
            }
        }

        return result;
    }

    private List<String> loadWorkshopNames(String category) {
        List<String> result = new ArrayList<>();
        List<Product> products;

        if (category.equals("")) {
            products = mWorkshops;
        } else {
            products = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByCategory(category);
        }

        for (int i = 0; i < products.size(); i++) {
            int productId = products.get(i).getProductId();

            if (!mSelectedWorkshops.contains(productId)) {
                result.add(products.get(i).getName());
            }
        }

        return result;
    }

    private void updateOrderOverview() {
        System.out.println("UPDATE ORDER OVERVIEW");
        mOverviewWorkshopRounds.setText("Workshoprondes: " + mCultureDayItem.getRounds());
        mOverviewDurationPerRound.setText("Duur per workshopronde: " + mCultureDayItem.getRoundDuration() + " min");
        mOverviewTotalDuration.setText("Tijdschema: " + ((mCultureDayItem.getTimeSchedule() == null || mCultureDayItem.getTimeSchedule().equals("")) ? "n.n.g." : mCultureDayItem.getTimeSchedule()));
        mOverviewTimeSchedule.setText("Totale duur: " + mCultureDayItem.getRoundDuration() * mCultureDayItem.getRounds() + " min");
        mOverviewLearningLevel.setText("Leerniveau: " + ((mCultureDayItem.getLearningLevel() == null || mCultureDayItem.getLearningLevel().equals("")) ? "n.n.b." : mCultureDayItem.getLearningLevel()));
        mOverviewTotalCost.setText("Subtotaal: €" + (int) mCultureDayItem.getPrice());
    }

    private boolean validate() {
        boolean result = true;

        boolean date = dateValidation.isValid();
        boolean participants = mCultureDayParticipantsValidator.isValid();
        boolean rounds = mRoundsValidator.isValid();
        boolean workshopsPerRound = mWorkshopsPerRoundValidator.isValid();
        boolean minutes = mCultureDayItem.getPrice() > 0;
        boolean workshops = mSelectedWorkshops.size() > 0;
        boolean schedule = mCultureDayItem.getTimeSchedule() != null || (mCultureDayItem.getTimeSchedule() != null ? mCultureDayItem.getTimeSchedule().length() : 0) > 0;
        boolean specialParticipants = participantsItemValidator.isValid();
        boolean level = mLearningLevelValidator.isValid();

        if (!date) {
            result = false;
            mDateEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!participants) {
            result = false;
            mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!rounds) {
            result = false;
            mWorkshopRoundsEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!workshopsPerRound) {
            result = false;
            mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!minutes) {
            result = false;
            mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!workshops) {
            result = false;
        }
        if (!schedule) {
            // set schedule to n.v.t. when left empty
            mCultureDayItem.setTimeSchedule("n.v.t.");
        }
        if (!specialParticipants) {
            result = false;
            mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!level) {
            result = false;
            mLearningLevelEditText.setBackgroundResource(R.drawable.edittext_error);
        }

        return result;
    }
}
