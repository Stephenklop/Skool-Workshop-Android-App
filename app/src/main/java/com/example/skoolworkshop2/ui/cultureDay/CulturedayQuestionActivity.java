package com.example.skoolworkshop2.ui.cultureDay;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.ui.MainActivity;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopQuestionActivity;

import java.time.LocalDate;

public class CulturedayQuestionActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
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

    private DatePickerDialog datePickerDialog;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureday_question);

        datePickerDialog = new DatePickerDialog(this, CulturedayQuestionActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());

        mSendBn = findViewById(R.id.activity_cultureday_question_btn_send);
        mBackButton = findViewById(R.id.activity_cultureday_question_btn_back);
        mEmailEditText = findViewById(R.id.activity_cultureday_question_et_email);
        mTelEditText = findViewById(R.id.activity_cultureday_question_et_phone);
        mAmountOfPersonsEditText = findViewById(R.id.activity_cultureday_question_et_amount);
        RelativeLayout rv = findViewById(R.id.activity_cultureday_question_ed_date);
        mDateEditText = rv.findViewById(R.id.date_picker_edit_text);
        mDatePopUpImageButton = rv.findViewById(R.id.component_edittext_date_calendar_btn_calendar);
        mTimeEditText = findViewById(R.id.activity_cultureday_question_et_time);
        mLocationEditText = findViewById(R.id.activity_cultureday_question_et_location);
        mCJPEditText = findViewById(R.id.activity_cultureday_question_et_cjp);
        mMessageEditText = findViewById(R.id.activity_cultureday_question_et_message);
        mNameEditText = findViewById(R.id.activity_cultureday_question_et_name);

        mSendBn.setText("Verzenden");

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
                Intent intent = new Intent(getBaseContext(), CulturedayActivity.class);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
