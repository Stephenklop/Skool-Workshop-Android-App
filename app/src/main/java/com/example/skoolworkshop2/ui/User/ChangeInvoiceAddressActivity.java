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
    private boolean billingChecker;
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
        billingChecker = getIntent().getBooleanExtra("CHECK", false);
        // First and Last name
        mFirstNameEditText = findViewById(R.id.activity_change_invoice_address_et_firstname);
        mLastNameEditText = findViewById(R.id.activity_change_invoice_address_et_lastname);
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
        mCompanyNameEditText = findViewById(R.id.activity_change_invoice_address_et_company);
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
        mLocationCountrySpnr = findViewById(R.id.activity_change_invoice_address_spnr_country);
        mLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Drawable[]{NL, BE}));
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
        mPlaceEditText = findViewById(R.id.activity_change_invoice_address_et_place);
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
        mStreetNameEditText = findViewById(R.id.activity_change_invoice_address_et_street);
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
        mCountryEditText = findViewById(R.id.activity_change_invoice_address_et_country);
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

        // Phone
        mTelEditText = findViewById(R.id.activity_change_invoice_address_et_tel);
        mTelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTelEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!telValidator.isValidTelNumber(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mTelEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(telValidator.isValidTelNumber(s.toString())){
                    mTelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    telValidator.mIsValid = true;
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
                mEmailEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!emailValidator.isValidEmail(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(emailValidator.isValidEmail(s.toString())){
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                    emailValidator.mIsValid = true;
                }
            }
        });

        // Type in edittexts if user updates billingaddress
        if (billingChecker){
            billingAddress = getIntent().getParcelableExtra("BILLINGADDRESS");
            mFirstNameEditText.setText(billingAddress.getFirstName());
            mLastNameEditText.setText(billingAddress.getLastName());
            mCompanyNameEditText.setText(billingAddress.getCompany());
            mPostCodeEditText.setText(billingAddress.getPostcode());
            // huisnummer
            mPlaceEditText.setText(billingAddress.getCity());
            mStreetNameEditText.setText(billingAddress.getAddress());
            mTelEditText.setText(billingAddress.getPhone());
            mEmailEditText.setText(billingAddress.getEmail());
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
        mSubmitButton = findViewById(R.id.activity_change_invoice_submit_btn);
        mSubmitButton.setText("Adres opslaan");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameValidator.isValid() && placeValidator.isValid() && houseNumberValidator.isValid() && countryValidator.isValid() && streetnameValidator.isValid() && telValidator.isValid() && emailValidator.isValid()) {
                    // Alles vgm opslaan in gebruiker
                    Intent intent = new Intent(getApplicationContext(), InvoiceAdressActivity.class);
                    if (billingChecker = false){
                        iem.insertBillingaddress(new BillingAddress(mFirstNameEditText.getText().toString(), mLastNameEditText.getText().toString(), mCompanyNameEditText.getText().toString(), mPostCodeEditText.getText().toString(), mPlaceEditText.getText().toString(),  mStreetNameEditText.getText().toString() + " " + mHouseNumberEditText.getText().toString(), mCountryEditText.getText().toString(), mTelEditText.getText().toString(), mEmailEditText.getText().toString()));
                    } else {
                        iem.deleteAdress(billingAddress.getId());
                        iem.insertBillingaddress(new BillingAddress(mFirstNameEditText.getText().toString(), mLastNameEditText.getText().toString(), mCompanyNameEditText.getText().toString(), mPostCodeEditText.getText().toString(), mPlaceEditText.getText().toString(),  mStreetNameEditText.getText().toString() + " " + mHouseNumberEditText.getText().toString(), mCountryEditText.getText().toString(), mTelEditText.getText().toString(), mEmailEditText.getText().toString()));
                    }
                    startActivity(intent);
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
                mPostCodeEditText.setText("");
            }
            mPostCodeEditText.removeTextChangedListener(beTextWatcher);
            mPostCodeEditText.addTextChangedListener(nlTextWatcher);
        } else if (item == BE) {
            Log.d(LOG_TAG, "onItemSelected: selected belgium");
            if (!mPostCodeEditText.getText().toString().isEmpty()) {
                mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                mPostCodeEditText.setText("");
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
