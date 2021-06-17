package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.ShippingAddress;
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

public class ChangeInvoiceShippingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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
    // Spinners
    private Spinner mLocationCountrySpnr;
    // Drawables
    private Drawable NL;
    private Drawable BE;
    // Button
    private ImageButton mBackButton;
    private Button mSubmitButton;
    // Checker
    private ShippingAddress shippingAddress;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_workshop_location);

        // Usermanager
        com.example.skoolworkshop2.logic.managers.localDb.UserManager iem = new UserManager(this.getApplication());

        // Back Button
        mBackButton = findViewById(R.id.activity_change_workshop_location_btn_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InvoiceAdressActivity.class));
            }
        });

        // Checker
        Log.d(LOG_TAG, "onCreate: boolean: " + InvoiceAdressActivity.billingChecker);

        // First and Last name
        mFirstNameEditText = findViewById(R.id.activity_change_workshop_location_et_firstname);
        mLastNameEditText = findViewById(R.id.activity_change_workshop_location_et_lastname);
        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFirstNameEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!nameValidator.isValidName(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mFirstNameEditText.setBackgroundResource(R.drawable.edittext_error);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nameValidator.isValidName(s.toString())){
                    mFirstNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    nameValidator.mIsValid = true;

                }
            }
        });
        mLastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLastNameEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!NameValidator.isValidName(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mLastNameEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nameValidator.isValidName(s.toString())){
                    mLastNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    nameValidator.mIsValid = true;
                }
            }
        });

        // Company
        mCompanyNameEditText = findViewById(R.id.activity_change_workshop_location_et_company);
        mCompanyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCompanyNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
            }
        });

        // Spinner
        NL = this.getDrawable(R.drawable.ic_flag_of_the_netherlands);
        BE = this.getDrawable(R.drawable.ic_flag_of_belgium);
        mLocationCountrySpnr = findViewById(R.id.activity_change_workshop_location_spnr_country);
        mLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Drawable[]{NL, BE}));
        mLocationCountrySpnr.setSelection(1);
        mLocationCountrySpnr.setOnItemSelectedListener(this);

        // Postal Code
        mPostCodeEditText = findViewById(R.id.activity_change_workshop_location_et_postalcode);

        // House Number
        mHouseNumberEditText = findViewById(R.id.activity_change_workshop_location_housenr);
        mHouseNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_focused);
                if(!houseNumberValidator.isValidAdressValidator(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_error);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (houseNumberValidator.isValidAdressValidator(s.toString())){
                    mHouseNumberEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    houseNumberValidator.mIsValid = true;
                }

            }
        });

        // Place
        mPlaceEditText = findViewById(R.id.activity_change_workshop_location_et_place);
        mPlaceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlaceEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!placeValidator.isValidPlace(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mPlaceEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(placeValidator.isValidPlace(s.toString())){
                    mPlaceEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    placeValidator.mIsValid = true;
                }
            }
        });

        // Street name
        mStreetNameEditText = findViewById(R.id.activity_change_workshop_location_et_street);
        mStreetNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStreetNameEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!streetnameValidator.isValidStreetname(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mStreetNameEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(streetnameValidator.isValidStreetname(s.toString())){
                    mStreetNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    streetnameValidator.mIsValid = true;
                }
            }
        });

        // Country
        mCountryEditText = findViewById(R.id.activity_change_workshop_location_et_country);
        mCountryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCountryEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!countryValidator.isValidCountry(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mCountryEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(countryValidator.isValidCountry(s.toString())){
                    mCountryEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    countryValidator.mIsValid = true;
                }
            }
        });

//        // Type in edittexts if user updates billingaddress
        if (InvoiceAdressActivity.shippingChecker = true) {
            shippingAddress = (ShippingAddress) getIntent().getSerializableExtra("SHIPPINGADDRESS");
            if (shippingAddress != null) {
                mFirstNameEditText.setText(shippingAddress.getFirstName());
                mLastNameEditText.setText(shippingAddress.getLastName());
                mCompanyNameEditText.setText(shippingAddress.getCompany());
                mPostCodeEditText.setText(shippingAddress.getPostcode());
                // huisnummer
                String[] parts = shippingAddress.getAddress().split(" ");
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

                mPlaceEditText.setText(shippingAddress.getCity());
                mStreetNameEditText.setText(stb.toString());
                mHouseNumberEditText.setText(house);
                mCountryEditText.setText(shippingAddress.getCountry());
            }
        }
        // Textwatchers
        nlTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!PostcodeValidatorNL.isValidPostcode(s.toString())) {
                    Log.d(LOG_TAG, "onTextChanged: verkeerde nederlandse postcode!!");
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PostcodeValidatorNL.isValidPostcode(s.toString())) {

                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);

                }
            }
        };
        beTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);

                if (!PostcodeValidatorBE.isValidPostcode(s.toString())) {
                    Log.d(LOG_TAG, "onTextChanged: verkeerde belgische postcode");
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PostcodeValidatorBE.isValidPostcode(s.toString())) {

                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);

                }
            }
        };

        // Submit Button
        mSubmitButton = findViewById(R.id.activity_change_workshop_location_btn_submit);
        mSubmitButton.setText("Adres opslaan");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameValidator.isValid() && placeValidator.isValid() && houseNumberValidator.isValid() && countryValidator.isValid() && streetnameValidator.isValid() && telValidator.isValid() && emailValidator.isValid()) {
                    // Making address
                    ShippingAddress address = new ShippingAddress(mFirstNameEditText.getText().toString(), mLastNameEditText.getText().toString(), mCompanyNameEditText.getText().toString(), mPostCodeEditText.getText().toString(), mPlaceEditText.getText().toString(), mStreetNameEditText.getText().toString() + " " + mHouseNumberEditText.getText().toString(), mCountryEditText.getText().toString());
                    if(InvoiceAdressActivity.shippingChecker = true){
                        // Making intent
                        Intent intent = new Intent(getApplicationContext(), InvoiceAdressActivity.class);
                        iem.deleteShippingAddress(address.getId());
                        iem.insertShippingAddress(address);
                        // Update user to link the billingaddress id
                        iem.updateInfo(new User(iem.getInfo().getId(), iem.getInfo().getEmail(), iem.getInfo().getUsername(), iem.getInfo().getPoints(), iem.getInfo().getShippingAddressId(), address.getId()));
                        // Let invoiceAddressActivity know there are item(s) now
                        InvoiceAdressActivity.shippingChecker = true;
                        // Start the activity
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), InvoiceAdressActivity.class);
                        // Inserting address into db
                        iem.insertShippingAddress(address);
                        // Update user to link the billingaddress id
                        iem.updateInfo(new User(iem.getInfo().getId(), iem.getInfo().getEmail(), iem.getInfo().getUsername(), iem.getInfo().getPoints(), iem.getInfo().getShippingAddressId(), address.getId()));
                        // Let invoiceAddressActivity know there are item(s) now
                        InvoiceAdressActivity.shippingChecker = true;
                        // Start the activity
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something isnt valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = mLocationCountrySpnr.getSelectedItem();
        if (item == NL) {
            Log.d(LOG_TAG, "onItemSelected: selected netherlands");
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            mPostCodeEditText.removeTextChangedListener(beTextWatcher);
            mPostCodeEditText.addTextChangedListener(nlTextWatcher);
        } else if (item == BE) {
            Log.d(LOG_TAG, "onItemSelected: selected belgium");
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            }
            mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
            mPostCodeEditText.removeTextChangedListener(nlTextWatcher);
            mPostCodeEditText.addTextChangedListener(beTextWatcher);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
