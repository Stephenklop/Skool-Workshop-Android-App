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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayBookingActivity;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WorkshopQuestionActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private String LOG_TAG = getClass().getSimpleName();

    private Button mSendBn;
    private ImageButton mBackButton;
    private EditText mEmailEditText;
    private EditText mTelEditText;
    private EditText mAmountOfPersonsEditText;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private EditText mLocationEditText;
    private EditText mCJPEditText;
    private EditText mMessageEditText;
    private EditText mNameEditText;
    private ImageButton mDatePopUpImageButton;
    private EmailValidator emailValidator = new EmailValidator();
    private TelValidator telValidator = new TelValidator();
    private TextView mTitleTextView;

    private DatePickerDialog datePickerDialog;

    private Product workshop;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_question);

        if(getIntent().getSerializableExtra("Workshop") != null){
            this.workshop = (Product) getIntent().getSerializableExtra("Workshop");
        }

        datePickerDialog = new DatePickerDialog(this, WorkshopQuestionActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());

        // Set up IDS
        mSendBn = findViewById(R.id.activity_workshop_question_btn_send);
        mBackButton = findViewById(R.id.activity_workshop_question_btn_back);
        mEmailEditText = (EditText) findViewById(R.id.activity_workshop_question_et_email);
        mTelEditText = (EditText) findViewById(R.id.activity_workshop_question_et_phone);
        mAmountOfPersonsEditText = (EditText) findViewById(R.id.activity_workshop_question_et_amount);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_workshop_question_et_date);
        mDateEditText = (EditText) rl.findViewById(R.id.date_picker_edit_text);
        mDatePopUpImageButton = (ImageButton) rl.findViewById(R.id.component_edittext_date_calendar_btn_calendar);
        mTimeEditText = (EditText) findViewById(R.id.activity_workshop_question_et_time);
        mLocationEditText = (EditText) findViewById(R.id.activity_workshop_question_et_location);
        mCJPEditText = (EditText) findViewById(R.id.activity_workshop_question_et_cjp);
        mMessageEditText = (EditText) findViewById(R.id.activity_workshop_question_et_message);
        mNameEditText = (EditText) findViewById(R.id.activity_workshop_question_et_name);
        //Title
        mTitleTextView = findViewById(R.id.activity_workshop_question_tv_title);
        mTitleTextView.setText(workshop.getName());

        // Set up validations
        mEmailEditText.addTextChangedListener(emailValidator);
        mTelEditText.addTextChangedListener(telValidator);

        mDatePopUpImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

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

                }
            }
        });


        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEmailEditText.setBackgroundResource(R.drawable.edittext_focused);
                if(!EmailValidator.isValidEmail(s)){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(EmailValidator.isValidEmail(s.toString())){
                    mEmailEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                }
            }
        });

        mTelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTelEditText.setBackgroundResource(R.drawable.edittext_focused);
                if(!TelValidator.isValidTelNumber(s)){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mTelEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TelValidator.isValidTelNumber(s.toString())){
                    mTelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                }
            }
        });

        mCJPEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCJPEditText.setBackgroundResource(R.drawable.edittext_focused);
                if(!CJPValidator.isValidCJP(s)){
                    Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                    mCJPEditText.setBackgroundResource(R.drawable.edittext_error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(CJPValidator.isValidCJP(s)){
                    mCJPEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                }
            }
        });



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WorkshopDetailActivity.class);
                intent.putExtra("Workshop", workshop);
                startActivity(intent);
            }
        });


        mSendBn.setText("Verzenden");
        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/html");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@skoolworkshop.nl"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email vanuit de app");

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Contact informatie:");
                    stringBuilder.append("\n-Naam: " + mNameEditText.getText().toString());
                    stringBuilder.append("\n-Email: " + mEmailEditText.getText().toString());
                    stringBuilder.append("\n-TelefoonNummer: " + mTelEditText.getText().toString());
                    stringBuilder.append("\nCJP schoolnummer: " + mCJPEditText.getText().toString());

                    stringBuilder.append("\n\nWorkshop informatie:");
                    stringBuilder.append("\n-Aantal personen: " + mAmountOfPersonsEditText.getText().toString());
                    stringBuilder.append("\n-Gewenste datum: " + mDateEditText.getText().toString());
                    stringBuilder.append("\n-Gewenste aanvangstijd: " + mTimeEditText.getText().toString());
                    stringBuilder.append("\n-Gewenste locatie: " + mLocationEditText.getText().toString());

                    stringBuilder.append("\n\nBericht:\n");
                    stringBuilder.append(mMessageEditText.getText().toString());

                    emailIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                    startActivity(emailIntent);

                } else {
                    System.out.println("something is empty");
                }
            }
        });
    }

    private boolean validate(){
        boolean returnValue =  true;
        boolean email = !mEmailEditText.getText().toString().isEmpty() && EmailValidator.isValidEmail(mEmailEditText.getText().toString());
        boolean amountOfPeople = !mAmountOfPersonsEditText.getText().toString().isEmpty();
        boolean date = !mDateEditText.getText().toString().isEmpty() && DateValidation.isValidDate(mDateEditText.getText().toString());
        boolean time = !mTimeEditText.getText().toString().isEmpty();
        boolean location = !mLocationEditText.getText().toString().isEmpty();
        boolean cjp = !mCJPEditText.getText().toString().isEmpty() && CJPValidator.isValidCJP(mCJPEditText.getText().toString());
        boolean name = !mNameEditText.getText().toString().isEmpty();
        boolean tel = !mTelEditText.getText().toString().isEmpty() && TelValidator.isValidTelNumber(mTelEditText.getText().toString());
        boolean message = !mMessageEditText.getText().toString().isEmpty();
        if(!email){
            returnValue = false;
            mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mEmailEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!amountOfPeople){
            returnValue = false;
            mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!date){
            returnValue = false;
            mDateEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mDateEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!time){
            returnValue = false;
            mTimeEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mTimeEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!location){
            returnValue = false;
            mLocationEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mLocationEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!cjp){
            returnValue = false;
            mCJPEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mCJPEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!name){
            returnValue = false;
            mNameEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!tel){
            returnValue = false;
            mTelEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mTelEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        if(!message){
            returnValue = false;
            mMessageEditText.setBackgroundResource(R.drawable.edittext_error);
        } else{
            mMessageEditText.setBackgroundResource(R.drawable.edittext_confirmed);
        }
        return returnValue;
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

