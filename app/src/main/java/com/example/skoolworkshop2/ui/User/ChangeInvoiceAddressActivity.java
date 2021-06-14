package com.example.skoolworkshop2.ui.User;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.validation.CJPValidator;
import com.example.skoolworkshop2.logic.validation.EmailValidator;
import com.example.skoolworkshop2.logic.validation.TelValidator;

public class ChangeInvoiceAddressActivity extends AppCompatActivity {

    //validations
    private TelValidator telValidator = new TelValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private CJPValidator cjpValidator = new CJPValidator();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_invoice_address);


    }
}
