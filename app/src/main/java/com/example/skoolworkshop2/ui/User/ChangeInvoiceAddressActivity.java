package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.UserDAO;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Country;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.CountryValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.HouseNumberValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.NameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.PlaceValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.StreetnameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorBE;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorNL;
import com.example.skoolworkshop2.ui.CountryArrayAdapter;

import java.sql.Statement;

public class ChangeInvoiceAddressActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private String LOG_TAG = getClass().getSimpleName();
    //validations
    private NameValidator nameValidator = new NameValidator();
    private PostcodeValidator postcodeValidatorNL = new PostcodeValidatorNL();
    private PostcodeValidator postcodeValidatorBE = new PostcodeValidatorBE();
    private HouseNumberValidator houseNumberValidator = new HouseNumberValidator();
    private PlaceValidator placeValidator = new PlaceValidator();
    private StreetnameValidator streetnameValidator = new StreetnameValidator();
    private TelValidator telValidator = new TelValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private CountryValidator countryValidator = new CountryValidator();

    private Country netherlands;
    private Country belgium;
    // Watchers
    private TextWatcher nlTextWatcher;
    private TextWatcher beTextWatcher;
    //Edit text
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mCompanyNameEditText;
    private EditText mPostCodeEditText;
    private EditText mHouseNumberEditText;
    private EditText mCountryEditText;
    private EditText mPlaceEditText;
    private EditText mStreetNameEditText;
    private EditText mTelEditText;
    private EditText mEmailEditText;
    // Spinners
    private Spinner mLocationCountrySpnr;
    // Drawables
    private Drawable NL;
    private Drawable BE;
    // Button
    private ImageButton mBackButton;
    private Button mSubmitButton;
    // Checker
    private BillingAddress billingAddress;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_invoice_address);

        // Usermanager
        com.example.skoolworkshop2.logic.managers.localDb.UserManager iem = new UserManager(this.getApplication());

        // Back Button
        mBackButton = findViewById(R.id.activity_change_invoice_address_btn_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InvoiceAdressActivity.class));
            }
        });

        // Checker
        Log.d(LOG_TAG, "onCreate: boolean: " + InvoiceAdressActivity.billingChecker);

        // First and Last name
        mFirstNameEditText = findViewById(R.id.activity_change_invoice_address_et_firstname);
        mLastNameEditText = findViewById(R.id.activity_change_invoice_address_et_lastname);
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
        // Company
        mCompanyNameEditText = findViewById(R.id.activity_change_invoice_address_et_company);
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

        // Spinner
        NL = this.getDrawable(R.drawable.ic_flag_of_the_netherlands);
        BE = this.getDrawable(R.drawable.ic_flag_of_belgium);
        netherlands = new Country(NL, "Nederland");
        belgium = new Country(BE, "België");
        mLocationCountrySpnr = findViewById(R.id.activity_change_invoice_address_spnr_country);
        mLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Country[]{netherlands, belgium}));
        mLocationCountrySpnr.setSelection(1);
        mLocationCountrySpnr.setOnItemSelectedListener(this);

        // Postal Code
        mPostCodeEditText = findViewById(R.id.activity_change_invoice_address_et_postalcode);

        // House Number
        mHouseNumberEditText = findViewById(R.id.activity_change_invoice_address_housenr);
        mHouseNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mHouseNumberEditText.equals("")) {
                    if (houseNumberValidator.isValidAdressValidator(s.toString())) {
                        mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        houseNumberValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_error);
                        houseNumberValidator.mIsValid = false;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mHouseNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(houseNumberValidator.isValid()) {
                        mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });
        // Place
        mPlaceEditText = findViewById(R.id.activity_change_invoice_address_et_place);
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

        // Street name
        mStreetNameEditText = findViewById(R.id.activity_change_invoice_address_et_street);
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

        // Country
        mCountryEditText = findViewById(R.id.activity_change_invoice_address_et_country);
        mCountryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mCountryEditText.equals("")) {
                    if (countryValidator.isValidCountry(s.toString())) {
                        mCountryEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        countryValidator.mIsValid = true;

                    } else {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mCountryEditText.setBackgroundResource(R.drawable.edittext_error);
                        countryValidator.mIsValid = false;

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mCountryEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(countryValidator.isValid()) {
                        mCountryEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mCountryEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });
        // Phone
        mTelEditText = findViewById(R.id.activity_change_invoice_address_et_tel);
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

        // Email
        mEmailEditText = findViewById(R.id.activity_change_invoice_address_et_email);
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

//        // Type in edittexts if user updates billingaddress
        if (InvoiceAdressActivity.billingChecker = true){
            billingAddress = (BillingAddress) getIntent().getSerializableExtra("BILLINGADDRESS");
            if (billingAddress != null) {
                mFirstNameEditText.setText(billingAddress.getFirstName());
                mLastNameEditText.setText(billingAddress.getLastName());
                mCompanyNameEditText.setText(billingAddress.getCompany());
                mPostCodeEditText.setText(billingAddress.getPostcode());
                // huisnummer
                String[] parts = billingAddress.getAddress().split(" ");
                StringBuilder stb = new StringBuilder();
                String house = "";
                for (String part : parts) {
                    if (part.matches(".*\\d.*")) {
                        house = part;
                    } else {
                        stb.append(part + " ");
                    }
                }
                Log.d(LOG_TAG, "onCreate: part 1: " + stb);
                Log.d(LOG_TAG, "onCreate: part 2: " + house);

                mPlaceEditText.setText(billingAddress.getCity());
                mStreetNameEditText.setText(stb.toString().trim());
                mHouseNumberEditText.setText(house);
                mTelEditText.setText(billingAddress.getPhone());
                mCountryEditText.setText(billingAddress.getCountry());
                mEmailEditText.setText(billingAddress.getEmail());
            }
        }

        // Textwatchers
        nlTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
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
                System.out.println(postcodeValidatorNL.isValid());
                System.out.println(postcodeValidatorBE.isValid());
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
                if(!s.equals("")) {
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

        // Submit Button
        mSubmitButton = findViewById(R.id.activity_change_invoice_submit_btn);
        mSubmitButton.setText("Adres opslaan");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOFactory apiDaoFactory = new APIDAOFactory();
                UserDAO userDAO = apiDaoFactory.getUserDAO();
                enableLoadingIndicator();

                if (nameValidator.isValid() && placeValidator.isValid() && houseNumberValidator.isValid() && countryValidator.isValid() && streetnameValidator.isValid() && telValidator.isValid() && emailValidator.isValid()) {
                    // Making address
                    BillingAddress address = new BillingAddress(mFirstNameEditText.getText().toString(), mLastNameEditText.getText().toString(), mCompanyNameEditText.getText().toString(), mPostCodeEditText.getText().toString(), mPlaceEditText.getText().toString(), mStreetNameEditText.getText().toString() + " " + mHouseNumberEditText.getText().toString(), mCountryEditText.getText().toString(), mTelEditText.getText().toString(), mEmailEditText.getText().toString());
                    // Making intent
                    Intent intent = new Intent(getApplicationContext(), InvoiceAdressActivity.class);
                    iem.deleteAdress();
                    iem.insertBillingaddress(address);
                    // Update user to link the billingaddress id
                    iem.updateInfo(new User(iem.getInfo().getId(), iem.getInfo().getEmail(), iem.getInfo().getUsername(), iem.getInfo().getPoints(), address.getId(), iem.getInfo().getShippingAddressId()));

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDAO.updateBilling(address);
                            startActivity(intent);
                        }
                    });
                    t.start();
                    // Start the activity

                } else {
                    Toast.makeText(getApplicationContext(), "Something isnt valid", Toast.LENGTH_SHORT).show();
                    disableLoadingIndicator();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = mLocationCountrySpnr.getSelectedItem();
        if (item == netherlands) {
            Log.d(LOG_TAG, "onItemSelected: selected netherlands");
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                if(PostcodeValidatorNL.isValidPostcode(mPostCodeEditText.getText().toString())){
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                } else {
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }
            mPostCodeEditText.removeTextChangedListener(beTextWatcher);
            mPostCodeEditText.addTextChangedListener(nlTextWatcher);
        } else if (item == belgium) {
            Log.d(LOG_TAG, "onItemSelected: selected belgium");
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                if(PostcodeValidatorBE.isValidPostcode(mPostCodeEditText.getText().toString())){
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }
            mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            mPostCodeEditText.removeTextChangedListener(nlTextWatcher);
            mPostCodeEditText.addTextChangedListener(beTextWatcher);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backGround = findViewById(R.id.activity_login_loading_background);
        backGround.setVisibility(View.VISIBLE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                avd.start();
            }
        });
        loadingAlert.setAlpha(0);
        loadingAlert.setVisibility(View.VISIBLE);
        loadingAlert.animate().alpha(1).setDuration(200).start();
        avd.start();
    }

    private void disableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backGround = findViewById(R.id.activity_login_loading_background);
        backGround.setVisibility(View.GONE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }
}
