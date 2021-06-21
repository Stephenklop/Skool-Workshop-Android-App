package com.example.skoolworkshop2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.logic.calculations.LocationCalculation;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.domain.Country;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.AddressValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.NameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.PlaceValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.StreetnameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorBE;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorNL;

public class AddressInfoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private String LOG_TAG = getClass().getSimpleName();

    // Data
    APIDAOFactory apidaoFactory = new APIDAOFactory();

    //validations
    private NameValidator nameValidator = new NameValidator();
    private PostcodeValidator postcodeValidatorNL = new PostcodeValidatorNL();
    private PostcodeValidator postcodeValidatorBE = new PostcodeValidatorBE();
    private AddressValidator addressValidator = new AddressValidator();
    private PlaceValidator placeValidator = new PlaceValidator();
    private StreetnameValidator streetnameValidator = new StreetnameValidator();
    private TelValidator telValidator = new TelValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private CJPValidator cjpValidator = new CJPValidator();

    private TextWatcher nlTextWatcher;
    private TextWatcher beTextWatcher;
    private TextWatcher nlWTextWatcher;
    private TextWatcher beWTextWatcher;

    //Edit text User
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mCompanyNameEditText;
    private EditText mPostCodeEditText;
    private EditText mHouseNr;
    private EditText mAddressEditText;
    private EditText mPlaceEditText;
    private EditText mStreetNameEditText;
    private EditText mTelEditText;
    private EditText mEmailEditText;
    private EditText mCJPEditText;
    private EditText mWorkshopInfoText;

    //Edit text Workshop
    private EditText mWFirstNameEditText;
    private EditText mWLastNameEditText;
    private EditText mWCompanyNameEditText;
    private EditText mWPostCodeEditText;
    private EditText mWAddressEditText;
    private EditText mWPlaceEditText;
    private EditText mWStreetNameEditText;
    private EditText mWHouseNr;

    // Radio buttons
    private RadioGroup mRegistrationSystemRadioGroup;
    private RadioGroup mCompilationRadioGroup;

    // Checkbox
    private CheckBox mShippingAddressCheckBox;

    private TextView mSubscribtionText;
    private TextView mErrTv;

    // Constraint Layout
    private ConstraintLayout mShippingAddressConstraintLayout;

    //Buttons
    private ImageButton mBackButton;
    private Button mSendBn;

    private Spinner mLocationCountrySpnr;
    private Spinner mWorkshopLocationCountrySpnr;

    private Country NL;
    private Country BE;

    private LocationCalculation locationCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        Context context = this;

        mSubscribtionText = findViewById(R.id.activity_address_info_tv_regsystem_info);
        mSubscribtionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoundedDialog roundedDialog = new RoundedDialog(context, "Online inschrijfsysteem", "Een cultuurdag organiseren is veel werk: roosters opzetten, presentielijsten maken en ervoor zorgen dat alle leerlingen ingeschreven staan. Met behulp van ons inschrijfsysteem worden al deze taken uit handen genomen! Leerlingen melden zich online aan en wij gaan met deze informatie aan de slag om alles in orde te maken. Het enige wat wij willen weten, is in welke lokalen/gymzalen de workshops gegeven kunnen worden. De workshop docenten zijn geheel zelfstandig, hierdoor ontstaat er ruimte voor de leerkracht voor andere klussen. Er hoeft dus geen leerkracht vanuit school aanwezig te zijn, uiteraard mag dit altijd. Op deze manier halen wij alle lasten tijdens het organiseren uit handen.");
            }
        });


//        initializeAttributes();


        //Setting up ID's user Info
        mFirstNameEditText = (EditText) findViewById(R.id.activity_address_info_et_firstname);
        mLastNameEditText = (EditText) findViewById(R.id.activity_address_info_et_lastname);
        mCompanyNameEditText = (EditText) findViewById(R.id.activity_address_info_et_company);
        mPostCodeEditText = (EditText) findViewById(R.id.activity_address_info_et_postalcode);
        mHouseNr = (EditText) findViewById(R.id.activity_address_info_housenr);
        mAddressEditText = (EditText) findViewById(R.id.activity_address_info_housenr);
        mPlaceEditText = (EditText) findViewById(R.id.activity_address_info_et_place);
        mStreetNameEditText = (EditText) findViewById(R.id.activity_address_info_et_street);
        mTelEditText = (EditText) findViewById(R.id.activity_address_info_et_tel);
        mEmailEditText = (EditText) findViewById(R.id.activity_address_info_et_email);
        mCJPEditText = (EditText) findViewById(R.id.activity_address_info_et_cjp);
        mWorkshopInfoText = (EditText) findViewById(R.id.activity_address_info_et_info);
        mSendBn = findViewById(R.id.activity_address_info_btn_submit);
        mBackButton = findViewById(R.id.activity_address_info_btn_back);

        //Setting up ID's Workshop Info
        mWFirstNameEditText = (EditText) findViewById(R.id.activity_address_info_et_workshop_firstname);
        mWLastNameEditText = (EditText) findViewById(R.id.activity_address_info_et_workshop_lastname);
        mWCompanyNameEditText = (EditText) findViewById(R.id.activity_address_info_et_workshop_company);
        mWPostCodeEditText = (EditText) findViewById(R.id.activity_address_info_et_workshop_postalcode);
        mWAddressEditText = (EditText) findViewById(R.id.activity_address_info_workshop_housenr);
        mWPlaceEditText = (EditText) findViewById(R.id.activity_address_info_et_workshop_place);
        mWStreetNameEditText = (EditText) findViewById(R.id.activity_address_info_et_workshop_street);
        mWHouseNr = (EditText) findViewById(R.id.activity_address_info_workshop_housenr);

        mRegistrationSystemRadioGroup = findViewById(R.id.activity_address_info_rg_regsystem);
        mCompilationRadioGroup = findViewById(R.id.activity_address_info_rg_comp);
        mErrTv = findViewById(R.id.activity_address_info_tv_err);

        NL = new Country(this.getDrawable(R.drawable.ic_flag_of_the_netherlands), "NL");
        BE = new Country(this.getDrawable(R.drawable.ic_flag_of_belgium), "BE");
        mLocationCountrySpnr = findViewById(R.id.activity_address_info_spnr_country);
        mLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Country[]{NL, BE}));
        mLocationCountrySpnr.setSelection(1);
        mLocationCountrySpnr.setOnItemSelectedListener(this);

        mWorkshopLocationCountrySpnr = findViewById(R.id.activity_address_info_spnr_workshop_country);
        mWorkshopLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Country[]{NL, BE}));
        mWorkshopLocationCountrySpnr.setSelection(1);

        mWorkshopLocationCountrySpnr.setOnItemSelectedListener(this);

        mShippingAddressCheckBox = findViewById(R.id.activity_address_info_cb_workshop_location);
        mShippingAddressConstraintLayout = findViewById(R.id.activity_address_info_cl_workshop_location);

        locationCalculation = new LocationCalculation();

        mShippingAddressCheckBox.setOnClickListener((View v) -> {
            if (mShippingAddressConstraintLayout.getVisibility() == View.VISIBLE) {
                mShippingAddressConstraintLayout.setVisibility(View.GONE);
            } else {
                mShippingAddressConstraintLayout.setVisibility(View.VISIBLE);
                if(LocalDb.getDatabase(getApplication()).getUserDAO().getShippingAddress(0) != null){
                    ShippingAddress shippingAddress = LocalDb.getDatabase(getApplication()).getUserDAO().getShippingAddress(0);
                    if(shippingAddress.getCountry().equals("Nederland")){
                        mWorkshopLocationCountrySpnr.setSelection(1);
                    } else {
                        mWorkshopLocationCountrySpnr.setSelection(2);
                    }

                    String addressSplit[] = shippingAddress.getAddress().split(" ");

                    mWFirstNameEditText.setText(shippingAddress.getFirstName());
                    mWLastNameEditText.setText(shippingAddress.getLastName());
                    mWCompanyNameEditText.setText(shippingAddress.getCompany());
                    mWPostCodeEditText.setText(shippingAddress.getPostcode());
                    mWHouseNr.setText(addressSplit[1]);
                    mWPlaceEditText.setText(shippingAddress.getCity());
                    mWStreetNameEditText.setText(addressSplit[0]);
                }
            }
        });

        // Textwatchers User
        nlTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPostCodeEditText.equals("")){
                    if (PostcodeValidatorNL.isValidPostcode(s.toString())){
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        postcodeValidatorNL.setmIsValid(true);


                    } else{
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                        postcodeValidatorNL.setmIsValid(false);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mPostCodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(postcodeValidatorNL.isValid() || postcodeValidatorBE.isValid()) {
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });
        beTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPostCodeEditText.equals("")) {
                    if (PostcodeValidatorBE.isValidPostcode(s.toString())) {
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        postcodeValidatorNL.setmIsValid(true);


                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                        postcodeValidatorNL.setmIsValid(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        //Textwatcher worksshop
        nlWTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!PostcodeValidatorNL.isValidPostcode(s.toString())) {
                    Log.d(LOG_TAG, "onTextChanged: verkeerde nederlandse postcode!!");
                    mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PostcodeValidatorNL.isValidPostcode(s.toString())) {

                    mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);

                }
            }
        };
        beWTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!PostcodeValidatorBE.isValidPostcode(s.toString())) {
                    Log.d(LOG_TAG, "onTextChanged: verkeerde belgische postcode");
                    mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PostcodeValidatorBE.isValidPostcode(s.toString())) {

                    mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);

                }
            }
        };



        //validations
        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!mFirstNameEditText.equals("")){
                    if (nameValidator.isValidName(s.toString())){
                        mFirstNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        nameValidator.mIsValid = true;

                    } else{
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mFirstNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        nameValidator.mIsValid = false;
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mFirstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(nameValidator.isValid()) {
                        mFirstNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mFirstNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mLastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mLastNameEditText.equals("")){
                    if (nameValidator.isValidName(s.toString())){
                        mLastNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        nameValidator.mIsValid = true;

                    } else{
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mLastNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        nameValidator.mIsValid = false;
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mLastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(nameValidator.isValid()) {
                        mLastNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mLastNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mAddressEditText.equals("")) {
                    if (addressValidator.isValidAdressValidator(s.toString())) {
                        mAddressEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        addressValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mAddressEditText.setBackgroundResource(R.drawable.edittext_error);
                        addressValidator.mIsValid = false;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mAddressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(addressValidator.isValid()) {
                        mAddressEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mAddressEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mCompanyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCompanyNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCompanyNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    mCompanyNameEditText.setBackgroundResource(R.drawable.edittext_default);

                } else{
                    mCompanyNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mPlaceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPlaceEditText.equals("")) {
                    if (PlaceValidator.isValidPlace(s.toString())) {
                        mPlaceEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        placeValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mPlaceEditText.setBackgroundResource(R.drawable.edittext_error);
                        placeValidator.mIsValid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPlaceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(placeValidator.isValid()) {
                        mPlaceEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mPlaceEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mStreetNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mStreetNameEditText.equals("")) {
                    if (StreetnameValidator.isValidStreetname(s.toString())) {
                        mStreetNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        streetnameValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mStreetNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        streetnameValidator.mIsValid = false;

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mStreetNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(streetnameValidator.isValid()) {
                        mStreetNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mStreetNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mTelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mTelEditText.equals("")) {
                    if (telValidator.isValidTelNumber(s.toString())) {
                        mTelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        telValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mTelEditText.setBackgroundResource(R.drawable.edittext_error);
                        telValidator.mIsValid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTelEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(telValidator.isValid()) {
                        mTelEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mTelEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });


        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mEmailEditText.equals("")) {
                    if (emailValidator.isValidEmail(s.toString())) {
                        mEmailEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        emailValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
                        emailValidator.mIsValid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(emailValidator.isValid()) {
                        mEmailEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mCJPEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mCJPEditText.equals("")) {
                    if (cjpValidator.isValidCJP(s.toString())) {
                        mCJPEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        cjpValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mCJPEditText.setBackgroundResource(R.drawable.edittext_error);
                        cjpValidator.mIsValid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mCJPEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(cjpValidator.isValid()) {
                        mCJPEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mCJPEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        // validator Workshop info
        mWFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mWFirstNameEditText.equals("")){
                    if (nameValidator.isValidName(s.toString())){
                        mWFirstNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        nameValidator.mIsValid = true;

                    } else{
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mWFirstNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        nameValidator.mIsValid = false;
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mWFirstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(nameValidator.isValid()) {
                        mWFirstNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mWFirstNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mWLastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mWLastNameEditText.equals("")) {
                    if (nameValidator.isValidName(s.toString())) {
                        mWLastNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        nameValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mWLastNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        nameValidator.mIsValid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mWLastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(nameValidator.isValid()) {
                        mWLastNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mWLastNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mWAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mWAddressEditText.equals("")) {
                    if (addressValidator.isValidAdressValidator(s.toString())) {
                        mWAddressEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        addressValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mWAddressEditText.setBackgroundResource(R.drawable.edittext_error);
                        addressValidator.mIsValid = false;
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mWAddressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(addressValidator.isValid()) {
                        mWAddressEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mWAddressEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });


        mWCompanyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWCompanyNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mWCompanyNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    mWCompanyNameEditText.setBackgroundResource(R.drawable.edittext_default);

                } else{
                    mWCompanyNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mWPlaceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mWPlaceEditText.equals("")) {
                    if (placeValidator.isValidPlace(s.toString())) {
                        mWPlaceEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        placeValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mWPlaceEditText.setBackgroundResource(R.drawable.edittext_error);
                        placeValidator.mIsValid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mWPlaceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(placeValidator.isValid()) {
                        mWPlaceEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mWPlaceEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mWStreetNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mWStreetNameEditText.equals("")) {
                    if (StreetnameValidator.isValidStreetname(s.toString())) {
                        mWStreetNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        streetnameValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mWStreetNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        streetnameValidator.mIsValid = false;

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mWStreetNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(streetnameValidator.isValid()) {
                        mWStreetNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mWStreetNameEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        mWorkshopInfoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWorkshopInfoText.setBackgroundResource(R.drawable.edittext_confirmed);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mWorkshopInfoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    mWorkshopInfoText.setBackgroundResource(R.drawable.edittext_default);

                } else{
                    mWorkshopInfoText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSendBn.setText("Verder");

        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    mErrTv.setVisibility(View.GONE);
                    Country billingAddressCountry = (Country) mLocationCountrySpnr.getSelectedItem();
                    Country shippingAddressCountry = (Country) mWorkshopLocationCountrySpnr.getSelectedItem();
                    RadioButton registrationSystemRadioButton = (RadioButton) findViewById(mRegistrationSystemRadioGroup.getCheckedRadioButtonId());
                    RadioButton compilationRadioButton = (RadioButton) findViewById(mCompilationRadioGroup.getCheckedRadioButtonId());
                    double distance = mShippingAddressCheckBox.isChecked() ? locationCalculation.getDistance(mWPostCodeEditText.getText().toString().replace(" ", ""), shippingAddressCountry.getName()) : locationCalculation.getDistance(mPostCodeEditText.getText().toString().replace(" ", ""), billingAddressCountry.getName());

                    BillingAddress billingAddress = new BillingAddress(
                            mFirstNameEditText.getText().toString(),
                            mLastNameEditText.getText().toString(),
                            mCompanyNameEditText.getText().toString(),
                            mPostCodeEditText.getText().toString(),
                            mPlaceEditText.getText().toString(),
                            "",
                            mStreetNameEditText.getText().toString() + " " + mAddressEditText.getText().toString(),
                            billingAddressCountry.getName(),
                            mTelEditText.getText().toString(),
                            mEmailEditText.getText().toString()
                    );

                    LocalDb.getDatabase(getBaseContext()).getBillingAddressDAO().deleteBillingAddress();
                    int billingAddressId = (int) LocalDb.getDatabase(getBaseContext()).getBillingAddressDAO().insertBillingAddress(billingAddress);

                    ShippingAddress shippingAddress;

                    if (mShippingAddressCheckBox.isChecked()) {
                        shippingAddress = new ShippingAddress(
                                mWFirstNameEditText.getText().toString(),
                                mWLastNameEditText.getText().toString(),
                                mWCompanyNameEditText.getText().toString(),
                                mWPostCodeEditText.getText().toString(),
                                mWPlaceEditText.getText().toString(),
                                "",
                                mWAddressEditText.getText().toString(),
                                shippingAddressCountry.getName()
                        );
                    } else {
                        shippingAddress = new ShippingAddress(
                                mFirstNameEditText.getText().toString(),
                                mLastNameEditText.getText().toString(),
                                mCompanyNameEditText.getText().toString(),
                                mPostCodeEditText.getText().toString(),
                                mPlaceEditText.getText().toString(),
                                "",
                                mAddressEditText.getText().toString(),
                                billingAddressCountry.getName()
                        );
                    }

                    LocalDb.getDatabase(getBaseContext()).getShippingAddressDAO().deleteShippingAddress();
                    LocalDb.getDatabase(getBaseContext()).getShippingAddressDAO().insertShippingAddress(shippingAddress);

                    UserManager userManager = new UserManager(getApplication());
                    Customer customer = userManager.getCustomer();

//                    long customerId = LocalDb.getDatabase(getBaseContext()).getCustomerDAO().addCustomer(
//                            new Customer(
//                                    mFirstNameEditText.getText().toString(),
//                                    mLastNameEditText.getText().toString(),
//                                    mEmailEditText.getText().toString(),
//                                    mStreetNameEditText.getText().toString(),
//                                    mAddressEditText.getText().toString(),
//                                    mPostCodeEditText.getText().toString(),
//                                    mPlaceEditText.getText().toString(),
//                                    mWorkshopLocationCountrySpnr.getSelectedItem().toString(),
//                                    mLocationCountrySpnr.getSelectedItem().toString()
//                            )
//                    );


                    // TODO: Add shipping address, billing video, reservation system, distance & price

                    LocalDb.getDatabase(getBaseContext()).getOrderDAO().deleteOrder();
                    LocalDb.getDatabase(getBaseContext()).getOrderDAO().insertOrder(
                            new Order(
                                    "pending",
                                    customer.getId(),
                                    billingAddressId,
                                    -1,
                                    "unknown",
                                    "unknown",
                                    mWorkshopInfoText.getText().toString().replace("\n", " "),
                                    Integer.parseInt((mCJPEditText.getText().toString().equals("")) ? "0" : mCJPEditText.getText().toString()),
                                    (String) registrationSystemRadioButton.getText(),
                                    (String) compilationRadioButton.getText(),
                                    Math.round(distance * 10.0) / 10.0,
                                    Math.round(distance * 0.56 * 100.0) / 100.0
                            )
                    );

                    new Thread(() -> apidaoFactory.getOrderDAO().addOrder(LocalDb.getDatabase(getBaseContext()).getOrderDAO().getOrder())).start();

                    Intent intent = new Intent(getBaseContext(), OrderSummaryActivity.class);
                    startActivity(intent);
                } else {
                    mErrTv.setVisibility(View.VISIBLE);
                    mErrTv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tv_err_translate_anim));
                }
            }
        });

        if(LocalDb.getDatabase(getApplication()).getUserDAO().getBillingAddress(0) != null){
            BillingAddress billingAddress = LocalDb.getDatabase(getApplication()).getUserDAO().getBillingAddress(0);

            if(billingAddress.getCountry().equals("Nederland")){
                mLocationCountrySpnr.setSelection(1);
            } else {
                mLocationCountrySpnr.setSelection(2);
            }

            String addressSplit[] = billingAddress.getAddress().split(" ");

            mFirstNameEditText.setText(billingAddress.getFirstName());
            mLastNameEditText.setText(billingAddress.getLastName());
            mCompanyNameEditText.setText(billingAddress.getCompany());
            mPostCodeEditText.setText(billingAddress.getPostcode());
            mHouseNr.setText(addressSplit[1]);
            mPlaceEditText.setText(billingAddress.getCity());
            mStreetNameEditText.setText(addressSplit[0]);
            mTelEditText.setText(billingAddress.getPhone());
            mEmailEditText.setText(billingAddress.getEmail());
        }


    }

    @Override
    public void onClick(View v) {

    }

    //user postcode spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Country item = (Country) mLocationCountrySpnr.getSelectedItem();
        Country itemWorkshop = (Country) mWorkshopLocationCountrySpnr.getSelectedItem();

        //USER
        if (item.getName().equals("NL")) {
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            mPostCodeEditText.removeTextChangedListener(beTextWatcher);
            mPostCodeEditText.addTextChangedListener(nlTextWatcher);

        } else if (item.getName().equals("BE")) {
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            mPostCodeEditText.removeTextChangedListener(nlTextWatcher);
            mPostCodeEditText.addTextChangedListener(beTextWatcher);
        }

        //Workshop
        if (itemWorkshop.getName().equals("NL")) {
            if (!mWPostCodeEditText.getText().toString().isEmpty()) {
                mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            mWPostCodeEditText.removeTextChangedListener(beWTextWatcher);
            mWPostCodeEditText.addTextChangedListener(nlWTextWatcher);
        } else if (itemWorkshop.getName().equals("BE")) {
            if (!mWPostCodeEditText.getText().toString().isEmpty()) {
                mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            mWPostCodeEditText.removeTextChangedListener(nlWTextWatcher);
            mWPostCodeEditText.addTextChangedListener(beWTextWatcher);
        }
    }





    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validate() {
        boolean result = true;

        boolean fName = NameValidator.isValidName(mFirstNameEditText.getText());
        boolean lName = NameValidator.isValidName(mLastNameEditText.getText());
        boolean postal = mLocationCountrySpnr.getSelectedItem().equals(NL) ? PostcodeValidatorNL.isValidPostcode(mPostCodeEditText.getText()) :  PostcodeValidatorBE.isValidPostcode(mPostCodeEditText.getText());
        boolean houseNr = AddressValidator.isValidAdressValidator(mAddressEditText.getText());
        boolean place = PlaceValidator.isValidPlace(mPlaceEditText.getText());
        boolean street = StreetnameValidator.isValidStreetname(mStreetNameEditText.getText());
        boolean tel = telValidator.isValid();
        boolean cjp = cjpValidator.isValid() || mCJPEditText.getText().toString().isEmpty();
        boolean email = EmailValidator.isValidEmail(mEmailEditText.getText());

        if (!fName) {
            result = false;
            mFirstNameEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!lName) {
            result = false;
            mLastNameEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!postal) {
            result = false;
            mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!houseNr) {
            result = false;
            mAddressEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!place) {
            result = false;
            mPlaceEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!street) {
            result = false;
            mStreetNameEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!tel) {
            result = false;
            mTelEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!email) {
            result = false;
            mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!cjp) {
            result = false;
            mCJPEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (mShippingAddressCheckBox.isChecked()) {
            boolean wFName = NameValidator.isValidName(mWFirstNameEditText.getText());
            boolean wLName = NameValidator.isValidName(mWLastNameEditText.getText());
            boolean wPostal = mLocationCountrySpnr.getSelectedItem().equals(NL) ? PostcodeValidatorNL.isValidPostcode(mWPostCodeEditText.getText()) :  PostcodeValidatorBE.isValidPostcode(mWPostCodeEditText.getText());
            boolean wHouseNr = AddressValidator.isValidAdressValidator(mWAddressEditText.getText());
            boolean wPlace = PlaceValidator.isValidPlace(mWPlaceEditText.getText());
            boolean wStreet = StreetnameValidator.isValidStreetname(mWStreetNameEditText.getText());

            if (!wFName) {
                result = false;
                mWFirstNameEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            if (!wLName) {
                result = false;
                mWLastNameEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            if (!wPostal) {
                result = false;
                mWPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            if (!wHouseNr) {
                result = false;
                mWAddressEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            if (!wPlace) {
                result = false;
                mWPlaceEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            if (!wStreet) {
                result = false;
                mWStreetNameEditText.setBackgroundResource(R.drawable.edittext_error);
            }
        }

        return result;
    }
}
