package com.example.skoolworkshop2.ui.cultureDay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.CultureDayItem;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.validation.LearningLevelValidator;
import com.example.skoolworkshop2.logic.validation.MinuteValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantsItemValidator;
import com.example.skoolworkshop2.logic.validation.RoundsValidator;
import com.example.skoolworkshop2.logic.validation.WorkshopsPerRoundValidator;
import com.example.skoolworkshop2.ui.cultureDay.adapters.CategoryArrayAdapter;
import com.example.skoolworkshop2.ui.cultureDay.adapters.WorkshopArrayAdapter;
import com.example.skoolworkshop2.ui.shoppingCart.ShoppingCartActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CultureDayBookingActivity extends FragmentActivity {
    private Product mCultureDay;
    private ShoppingCartItem mShoppingCartItem;
    private CultureDayItem mCultureDayItem;
    private List<Integer> mSelectedWorkshops;
    private List<Product> mWorkshops;
    private List<String> mWorkshopCategories;
    private List<String> mWorkshopNames;
    private ImageButton mBackButton;
    private ImageView mBanner;
    private TextView mTitle;
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

    private TextView mOverviewWorkshopRounds;
    private TextView mOverviewDurationPerRound;
    private TextView mOverviewTotalDuration;
    private TextView mOverviewTimeSchedule;
    private TextView mOverviewLearningLevel;
    private TextView mOverviewTotalCost;
    private Button mOrderButton;

    private CultureDayParticipantsValidator mCultureDayParticipantsValidator;
    private WorkshopsPerRoundValidator mWorkshopsPerRoundValidator;
    private MinuteValidator mMinuteValidator;
    private LearningLevelValidator mLearningLevelValidator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_booking);

        // Initialize attributes
        initializeAttributes();

        mBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CulturedayActivity.class);
            startActivity(intent);
        });

        Glide.with(getBaseContext()).load(mCultureDay.getSourceImage()).into(mBanner);

        mTitle.setText(mCultureDay.getName());

        // TODO: Add date validation (to ensure the date is in the future)
        mDateEditText.setOnClickListener(v -> setDatePicker());
        mDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setDate(s.toString());
            }
        });

        mParticipantsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mParticipantsEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!CultureDayParticipantsValidator.isValidMaxParticipant(s.toString())) {
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_error);
                } else {
                    mParticipantsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mCultureDayParticipantsValidator.mIsValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setParticipants((s.toString().equals("")) ? 0 : Integer.parseInt(s.toString()));
            }
        });

        mWorkshopRoundsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!RoundsValidator.isValidWorkshopRounds(s.toString())) {
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
                } else {
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mWorkshopsPerRoundValidator.mIsValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setRounds(Integer.parseInt(s.toString()));
                updateOrderOverview();
            }
        });

        mWorkshopsPerRoundEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!RoundsValidator.isValidWorkshopRounds(s.toString())) {
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
                } else {
                    mWorkshopsPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mWorkshopsPerRoundValidator.mIsValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setWorkshopPerWorkshopRound(Integer.parseInt(s.toString()));
                updateOrderOverview();
            }
        });

        mDurationPerRoundEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!MinuteValidator.isValidMinute(s.toString())) {
                    mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_error);
                } else {
                    mDurationPerRoundEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mMinuteValidator.mIsValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCultureDayItem.setRoundDuration(Integer.parseInt(s.toString()));
                updateOrderOverview();
            }
        });

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWorkshopNames = loadWorkshopNames(mWorkshopCategories.get(position));
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

                    Button button = new Button(getBaseContext());
                    button.setText(selectedItem);

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
                }

                mShoppingCartItem.setTimeSchedule(s.toString());
                updateOrderOverview();
            }
        });

        mParticipantsGraffitiThsirtEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_focused);
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO: Check if maxParticipants is indeed 25
                if(ParticipantsItemValidator.isValidParticipantsItemValidator(s.toString(), 25)){
                    mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    mCultureDayItem.setAmountOfParticipantsGraffitiTshirt(Integer.parseInt(s.toString()));
                    updateOrderOverview();
                } else {
                    mParticipantsGraffitiThsirtEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }
        });

        mLearningLevelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO: Check learning level validator
                if(!mLearningLevelValidator.isValidLearningLevels(s.toString())){
                    mLearningLevelEditText.setBackgroundResource(R.drawable.edittext_error);
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

        mOrderButton.setText("Boek nu");
        mOrderButton.setOnClickListener(v -> {
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
                    mCultureDayItem.getPrice()
            );

            shoppingCartItem.setProducts(mSelectedWorkshops);

            System.out.println("BOOKED CULTURE DAY: " + shoppingCartItem);
            System.out.println("WORKSHOPS: " + mSelectedWorkshops);

            LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().insertItemInShoppingCart(shoppingCartItem);

            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        });
    }

    private void initializeAttributes() {
        // Main view
        mCultureDay = (Product) getIntent().getSerializableExtra("cultureDay");
        mShoppingCartItem = new ShoppingCartItem();
        mCultureDayItem = new CultureDayItem(mCultureDay);
        mSelectedWorkshops = new ArrayList<>();
        mWorkshops = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByType("Workshop");
        mWorkshopCategories = loadWorkshopCategories();
        mWorkshopNames = loadWorkshopNames("");
        mBackButton = findViewById(R.id.activity_cultureday_booking_btn_back);
        mBanner = findViewById(R.id.activity_cultureday_booking_img_banner);
        mTitle = findViewById(R.id.activity_cultureday_booking_tv_title);
        mDateEditText = findViewById(R.id.date_picker_edit_text);
        mParticipantsEditText = findViewById(R.id.activity_cultureday_booking_et_amount).findViewById(R.id.number_edit_text);
        mWorkshopRoundsEditText = findViewById(R.id.activity_cultureday_booking_et_rounds);
        mWorkshopsPerRoundEditText = findViewById(R.id.activity_cultureday_booking_et_workshops);
        mDurationPerRoundEditText = findViewById(R.id.activity_cultureday_booking_et_mins);
        mCategorySpinner = findViewById(R.id.activity_cultureday_booking_spnr_category);
        mCategoryArrayAdapter = new CategoryArrayAdapter(this, R.layout.item_spinner_dropdown, mWorkshopCategories); //new ArrayAdapter<>(this, R.layout.item_spinner_dropdown, mWorkshopCategories);
        mWorkshopSpinner = findViewById(R.id.activity_cultureday_booking_spnr_workshop);
        mWorkshopArrayAdapter = new WorkshopArrayAdapter(this, R.layout.item_spinner_dropdown, mWorkshopNames);
        mWorkshopsLinearLayout = findViewById(R.id.activity_cultureday_booking_workshops);
        mTimeScheduleEditText = findViewById(R.id.schedule_edit_text);
        mParticipantsGraffitiThsirtEditText = findViewById(R.id.activity_cultureday_booking_et_special_workshops).findViewById(R.id.number_edit_text);
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
    }

    private void setDatePicker() {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            mDateEditText.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            mDateEditText.setError(null);
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private List<String> loadWorkshopCategories() {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < mWorkshops.size(); i++) {
            String category = mWorkshops.get(i).getCategory();

            // Avoid duplicate categories showing up
            if (result.stream().noneMatch(o -> o.equals(category))) {
                // TODO: Fix empty category
                result.add(category);
            }
        }

        return result;
    }

    private List<String> loadWorkshopNames(String category) {
        List<String> result = new ArrayList<>();
        List<Product> products;

        // TODO: Add all category
        if (category.equals("")) {
            products = mWorkshops;
        } else {
            products = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByCategory(category);
        }

        for (int i = 0; i < products.size(); i++) {
            result.add(products.get(i).getName());
        }

        return result;
    }

    private void updateOrderOverview() {
        System.out.println("UPDATE ORDER OVERVIEW");
        mOverviewWorkshopRounds.setText("Workshoprondes: " + mShoppingCartItem.getRounds());
        mOverviewDurationPerRound.setText("Duur per workshopronde: " + mShoppingCartItem.getRoundDuration() + " min");
        mOverviewTotalDuration.setText("Tijdschema: " + ((mShoppingCartItem.getTimeSchedule() == null || mShoppingCartItem.getTimeSchedule().equals("")) ? "n.n.g." : mShoppingCartItem.getTimeSchedule()));
        mOverviewTimeSchedule.setText("Totale duur: " + mShoppingCartItem.getRoundDuration() * mShoppingCartItem.getRounds() + " min");
        mOverviewLearningLevel.setText("Leerniveau: " + ((mShoppingCartItem.getLearningLevel() == null || mShoppingCartItem.getLearningLevel().equals("")) ? "n.n.b." : mShoppingCartItem.getLearningLevel()));
        mOverviewTotalCost.setText("Subtotaal: €" + (int) mShoppingCartItem.getTotalPrice());
    }
}
