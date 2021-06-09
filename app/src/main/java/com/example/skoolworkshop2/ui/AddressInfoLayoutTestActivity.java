package com.example.skoolworkshop2.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.CultureDayParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.AddressValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.NameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.PlaceValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.StreetnameValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorBE;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.postcodeValidator.PostcodeValidatorNL;

public class AddressInfoLayoutTestActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private String LOG_TAG = getClass().getSimpleName();

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

    //Edit text
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mCompanyNameEditText;
    private EditText mPostCodeEditText;
    private EditText mAddressEditText;
    private EditText mPlaceEditText;
    private EditText mStreetNameEditText;
    private EditText mTelEditText;
    private EditText mEmailEditText;
    private EditText mCJPEditText;
    private EditText mWorkshopInfoText;

    //Buttons
    private ImageButton mBackButton;
    private Button mSendBn;

    private Spinner mLocationCountrySpnr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);


        //Setting up ID's
        mFirstNameEditText = (EditText) findViewById(R.id.activity_address_info_et_firstname);
        mLastNameEditText = (EditText) findViewById(R.id.activity_address_info_et_lastname);
        mCompanyNameEditText = (EditText) findViewById(R.id.activity_address_info_et_company);
        mPostCodeEditText = (EditText) findViewById(R.id.activity_address_info_et_postalcode);
        mAddressEditText = (EditText) findViewById(R.id.activity_address_info_housenr);
        mPlaceEditText = (EditText) findViewById(R.id.activity_address_info_et_place);
        mStreetNameEditText = (EditText) findViewById(R.id.activity_address_info_et_street);
        mTelEditText = (EditText) findViewById(R.id.activity_address_info_et_tel);
        mEmailEditText = (EditText) findViewById(R.id.activity_address_info_et_email);
        mCJPEditText = (EditText) findViewById(R.id.activity_address_info_et_cjp);
        mWorkshopInfoText = (EditText) findViewById(R.id.activity_address_info_et_info);
        mSendBn = findViewById(R.id.activity_address_info_btn_submit);

        Drawable NL = this.getDrawable(R.drawable.ic_flag_of_the_netherlands);
        Drawable BE = this.getDrawable(R.drawable.ic_flag_of_belgium);
        mLocationCountrySpnr = findViewById(R.id.activity_address_info_spnr_country);
        mLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Drawable[]{NL, BE}));
        mLocationCountrySpnr.setSelection(1);
        mLocationCountrySpnr.setOnItemSelectedListener(this);


        Spinner mWorkshopLocationCountrySpnr = findViewById(R.id.activity_address_info_spnr_workshop_country);
        mWorkshopLocationCountrySpnr.setAdapter(new CountryArrayAdapter(this, new Drawable[]{NL, BE}));
        mWorkshopLocationCountrySpnr.setSelection(1);


        CheckBox mWorkshopLocationCb = findViewById(R.id.activity_address_info_cb_workshop_location);
        ConstraintLayout mWorkshopLocationCl = findViewById(R.id.activity_address_info_cl_workshop_location);

        mWorkshopLocationCb.setOnClickListener((View v) -> {
            if (mWorkshopLocationCl.getVisibility() == View.VISIBLE) {
                mWorkshopLocationCl.setVisibility(View.GONE);
            } else {
                mWorkshopLocationCl.setVisibility(View.VISIBLE);
            }
        });

        //validations
        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFirstNameEditText.setBackgroundResource(R.drawable.edittext_focused);

                if(!NameValidator.isValidNameValidator(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mFirstNameEditText.setBackgroundResource(R.drawable.edittext_error);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (NameValidator.isValidNameValidator(s.toString())){
                    mFirstNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);


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

                if(!NameValidator.isValidNameValidator(s.toString())){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mLastNameEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (NameValidator.isValidNameValidator(s.toString())){
                    mLastNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);


                }
            }
        });


    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int item = mLocationCountrySpnr.getId();

        if (item == R.drawable.ic_flag_of_belgium) {
            Log.d(LOG_TAG, "onItemSelected: postcode validation now in: " + item);
            mPostCodeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);

                    if (!PostcodeValidatorNL.isValidPostcode(s.toString())) {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (PostcodeValidatorNL.isValidPostcode(s.toString())) {

                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);

                    }
                }
            });
        } else if (item == R.drawable.ic_flag_of_the_netherlands) {
            mPostCodeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mPostCodeEditText.setBackgroundResource(R.drawable.edittext_focused);

                    if (!PostcodeValidatorBE.isValidPostcode(s.toString())) {
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_error);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (PostcodeValidatorBE.isValidPostcode(s.toString())) {

                        mPostCodeEditText.setBackgroundResource(R.drawable.edittext_confirmed);

                    }
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
