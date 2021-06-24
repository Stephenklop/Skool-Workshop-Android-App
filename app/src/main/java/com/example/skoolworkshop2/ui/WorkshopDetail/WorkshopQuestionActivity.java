package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.activity_email_result;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.Mail;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.DateValidation;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.ParticipantFactoryPattern.WorkshopParticipantsValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.logic.validation.addressInfoValidators.NameValidator;
import com.example.skoolworkshop2.ui.SplashScreenActivity;

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
    private CheckBox mTermsCb;
    private TextView mErrTv;
    private ImageButton mDatePopUpImageButton;
    private EmailValidator emailValidator = new EmailValidator();
    private TelValidator telValidator = new TelValidator();
    private DateValidation dateValidation = new DateValidation();
    private NameValidator nameValidator = new NameValidator();
    private CJPValidator cjpValidator = new CJPValidator();
    private WorkshopParticipantsValidator workshopParticipantsValidator = new WorkshopParticipantsValidator();
    private TextView mTitleTextView;

    private DatePickerDialog datePickerDialog;

    private Product workshop;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_question);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        if(getIntent().getSerializableExtra("workshop") != null){
            this.workshop = (Product) getIntent().getSerializableExtra("workshop");
        }

        Button costsButton = findViewById(R.id.activity_workshop_question_button_price);
        Button participantsButton = findViewById(R.id.activity_workshop_question_button_participants);
        Button durationButton = findViewById(R.id.activity_workshop_question_button_duration);

        costsButton.setText("€150,-");
        participantsButton.setText("25 deelnemers");
        durationButton.setText("60 min");

        ImageView backgroundImage = findViewById(R.id.activity_workshop_question_img_banner);
        Glide.with(getBaseContext()).load(workshop.getSourceImage()).centerCrop().into(backgroundImage);

        datePickerDialog = new DatePickerDialog(this, R.style.Theme_SkoolWorkshop2_DatePicker, WorkshopQuestionActivity.this, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());

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
        mTermsCb = findViewById(R.id.activity_workshop_question_cb_terms);
        mErrTv = findViewById(R.id.activity_workshop_question_tv_err);

        // Set up validations
        mAmountOfPersonsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mAmountOfPersonsEditText.equals("")) {
                    if (workshopParticipantsValidator.isValidMaxParticipant(s.toString())) {

                        mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        workshopParticipantsValidator.mIsValid = true;
                    } else if (!workshopParticipantsValidator.isValidMaxParticipant(s.toString())) {
                        mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_error);
                        workshopParticipantsValidator.mIsValid = false;
                    } else {
                        mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_focused);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAmountOfPersonsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(workshopParticipantsValidator.isValid()) {
                        mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_focused);
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

        mDateEditText.setFocusable(false);
        mDatePopUpImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        mDateEditText.setOnClickListener(new View.OnClickListener() {
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
                if(!mDateEditText.equals("")) {
                    if (dateValidation.isValidDate(charSequence.toString())) {
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
            public void afterTextChanged(Editable editable) {

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
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!mNameEditText.equals("")){
                    if (nameValidator.isValidName(s.toString())){
                        mNameEditText.setBackgroundResource(R.drawable.edittext_confirmed);
                        nameValidator.mIsValid = true;

                    } else{
                        Log.d(LOG_TAG, "onTextChanged: FOUT!!");
                        mNameEditText.setBackgroundResource(R.drawable.edittext_error);
                        nameValidator.mIsValid = false;
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(nameValidator.isValid()) {
                        mNameEditText.setBackgroundResource(R.drawable.edittext_default);
                    }
                } else{
                    mNameEditText.setBackgroundResource(R.drawable.edittext_focused);
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
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mMessageEditText.setBackgroundResource(R.drawable.edittext_confirmed);

            }
        });
        mMessageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                        mMessageEditText.setBackgroundResource(R.drawable.edittext_default);

                } else{
                    mMessageEditText.setBackgroundResource(R.drawable.edittext_focused);

                }
            }
        });

        mTermsCb.setOnClickListener(v -> {
            if (mTermsCb.isChecked()) {
                mTermsCb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.main_orange, null)));
            }
        });



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mSendBn.setText("Verzenden");
        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    mErrTv.setVisibility(View.GONE);

                    Mail mail = new Mail(Integer.parseInt(mAmountOfPersonsEditText.getText().toString()), mDateEditText.getText().toString(), mTimeEditText.getText().toString(), mLocationEditText.getText().toString(), Integer.parseInt(mCJPEditText.getText().toString()), mEmailEditText.getText().toString(), mTelEditText.getText().toString(), mMessageEditText.getText().toString());
                    DAOFactory daoFactory = new APIDAOFactory();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                daoFactory.getEmailDAO().sendMail(mail);
                                startActivity(new Intent(WorkshopQuestionActivity.this, activity_email_result.class).putExtra("workshop", workshop));
                            } catch (Exception e){
                                startActivity(new Intent(WorkshopQuestionActivity.this, activity_email_result.class).putExtra("workshop", workshop).putExtra("failed", true));
                            }
                        }
                    }).start();

                } else {
                    mErrTv.setVisibility(View.VISIBLE);
                    mErrTv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tv_err_translate_anim));
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
        boolean cjp = mCJPEditText.getText().toString().isEmpty() || CJPValidator.isValidCJP(mCJPEditText.getText().toString());
        boolean name = !mNameEditText.getText().toString().isEmpty();
        boolean tel = !mTelEditText.getText().toString().isEmpty() && TelValidator.isValidTelNumber(mTelEditText.getText().toString());
        boolean message = !mMessageEditText.getText().toString().isEmpty();
        boolean terms = mTermsCb.isChecked();
        if(!email){
            returnValue = false;
            mEmailEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!amountOfPeople){
            returnValue = false;
            mAmountOfPersonsEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!date){
            returnValue = false;
            mDateEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!time){
            returnValue = false;
            mTimeEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!location){
            returnValue = false;
            mLocationEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!cjp){
            returnValue = false;
            mCJPEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!name){
            returnValue = false;
            mNameEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!tel){
            returnValue = false;
            mTelEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if(!message){
            returnValue = false;
            mMessageEditText.setBackgroundResource(R.drawable.edittext_error);
        }
        if (!terms) {
            returnValue = false;
            mTermsCb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_red, null)));
        } else {
            mTermsCb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.main_orange, null)));
        }
        return returnValue;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int indexOneMonth = month + 1;
        if(dayOfMonth < 10 && month < 10){
            mDateEditText.setText("0" + dayOfMonth + "/0" + indexOneMonth + "/" + year);
        } else if (dayOfMonth < 10){
            mDateEditText.setText("0" + dayOfMonth + "/" + indexOneMonth + "/" + year);
        } else if (month < 10){
            mDateEditText.setText(dayOfMonth + "/0" + indexOneMonth + "/" + year);
        } else {
            mDateEditText.setText(dayOfMonth + "/" + indexOneMonth + "/" + year);
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

