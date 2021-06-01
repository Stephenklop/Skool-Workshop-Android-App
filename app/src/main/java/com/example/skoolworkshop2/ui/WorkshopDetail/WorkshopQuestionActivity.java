package com.example.skoolworkshop2.ui.WorkshopDetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;
import com.example.skoolworkshop2.ui.MainActivity;

public class WorkshopQuestionActivity extends FragmentActivity implements View.OnClickListener {
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
    private EmailValidator emailValidator = new EmailValidator();
    private TelValidator telValidator = new TelValidator();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_question);

        // Set up IDS
        mSendBn = findViewById(R.id.activity_workshop_question_btn_send);
        mBackButton = findViewById(R.id.activity_workshop_question_btn_back);
        mEmailEditText = (EditText) findViewById(R.id.activity_workshop_question_et_email);
        mTelEditText = (EditText) findViewById(R.id.activity_workshop_question_et_phone);
        mAmountOfPersonsEditText = (EditText) findViewById(R.id.activity_workshop_question_et_amount);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_workshop_question_et_date);
        mDateEditText = (EditText) rl.findViewById(R.id.editText);
        mTimeEditText = (EditText) findViewById(R.id.activity_workshop_question_et_time);
        mLocationEditText = (EditText) findViewById(R.id.activity_workshop_question_et_location);
        mCJPEditText = (EditText) findViewById(R.id.activity_workshop_question_et_cjp);
        mMessageEditText = (EditText) findViewById(R.id.activity_workshop_question_et_message);
        mNameEditText = (EditText) findViewById(R.id.activity_workshop_question_et_name);

        // Set up validations
        mEmailEditText.addTextChangedListener(emailValidator);
        mTelEditText.addTextChangedListener(telValidator);



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        mSendBn.setText("Verzenden");
        mSendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mAmountOfPersonsEditText.getText().toString());
                System.out.println(mDateEditText.getText().toString());
                System.out.println(mTimeEditText.getText().toString());
                System.out.println(mLocationEditText.getText().toString());
                System.out.println(mCJPEditText.getText().toString());
                System.out.println(mEmailEditText.getText().toString());
                System.out.println(mTelEditText.getText().toString());
                System.out.println(mMessageEditText.getText().toString());
                if(!mEmailEditText.getText().toString().isEmpty()){
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
                    System.out.println("Email is empty");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

