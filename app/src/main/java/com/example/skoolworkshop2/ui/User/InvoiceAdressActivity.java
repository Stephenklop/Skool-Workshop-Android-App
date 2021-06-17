package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.domain.User;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.ui.AddressInfoLayoutTestActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InvoiceAdressActivity extends AppCompatActivity {

    private String LOG_TAG = getClass().getSimpleName();
    // BillingAddress
    private TextView minvoiceBillingTitleTextView;
    private ConstraintLayout mInvoiceAddressLayout;
    private ImageButton mInvoiceBillingAddressImageButton;
    public static BillingAddress billingAddress;
    private TextView mInvoiceBillingTextView;
    // BillingAddress
    private TextView mInvoiceShippingTitleTextView;
    private ConstraintLayout mInvoiceShippingAddressLayout;
    private ImageButton mInvoiceShippingAddressImageButton;
    private ShippingAddress shippingAddress;
    private TextView mInvoiceShippingTextView;
    // check for update
    public static boolean billingChecker = false;
    public static boolean shippingChecker = false;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_data);

        // Billing
        mInvoiceAddressLayout = findViewById(R.id.activity_invoice_data_item_invoice_address);
        mInvoiceBillingAddressImageButton = mInvoiceAddressLayout.findViewById(R.id.component_invoice_data_btn_edit);
        mInvoiceBillingTextView = mInvoiceAddressLayout.findViewById(R.id.component_invoice_data_tv_data);
        minvoiceBillingTitleTextView = mInvoiceAddressLayout.findViewById(R.id.component_invoice_data_tv_header);
        minvoiceBillingTitleTextView.setText("Factuuradres");
        // Shipping
        mInvoiceShippingAddressLayout = findViewById(R.id.activity_invoice_data_item_workshop_location);
        mInvoiceShippingAddressImageButton = mInvoiceShippingAddressLayout.findViewById(R.id.component_invoice_data_btn_edit);
        mInvoiceShippingTextView = mInvoiceShippingAddressLayout.findViewById(R.id.component_invoice_data_tv_data);
        mInvoiceShippingTitleTextView = mInvoiceShippingAddressLayout.findViewById(R.id.component_invoice_data_tv_header);
        mInvoiceShippingTitleTextView.setText("Workshop Locatie");
        // Usermanager
        com.example.skoolworkshop2.logic.managers.localDb.UserManager iem = new UserManager(this.getApplication());
        // Loading billing addresses
        Log.d(LOG_TAG, "onCreate: " + iem.getAddresses());
        Log.d(LOG_TAG, "onCreate: id: " + iem.getInfo().getBillingAddressId());
        billingAddress = iem.getBillingAddress(iem.getInfo().getBillingAddressId());
        Log.d(LOG_TAG, "onCreate: billingaddress: " + billingAddress);
        // Loading shipping adresses
        Log.d(LOG_TAG, "onCreate: " + iem.getShippingAddresses());
        Log.d(LOG_TAG, "onCreate: id: " + iem.getInfo().getShippingAddressId());
        shippingAddress = iem.getShippingAddress(iem.getInfo().getShippingAddressId());
        Log.d(LOG_TAG, "onCreate: shippingAddress: " + shippingAddress);
        // checking if layout should contain object or not
        if (billingAddress != null){
            billingChecker = true;
            mInvoiceBillingTextView.setText(billingAddress.toString());
            mInvoiceBillingAddressImageButton.setBackgroundResource(R.drawable.ic_edit);

            mInvoiceBillingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBillingaddress = new Intent(getApplicationContext(), ChangeInvoiceAddressActivity.class);
                    toBillingaddress.putExtra("BILLINGADDRESS", (Serializable) billingAddress);
                    toBillingaddress.putExtra("CHECK", billingChecker);
                    startActivity(toBillingaddress);
                }
            });
        } else if (billingAddress == null){
            billingChecker = false;
            mInvoiceBillingTextView.setText("Dit adres is nog niet ingesteld.");
            mInvoiceBillingAddressImageButton.setBackgroundResource(R.drawable.ic_plus);
            mInvoiceBillingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBillingaddress = new Intent(getApplicationContext(), ChangeInvoiceAddressActivity.class);
                    toBillingaddress.putExtra("CHECK", billingChecker);
                    startActivity(toBillingaddress);
                }
            });
        }

        if(shippingAddress != null){
            shippingChecker = true;
            mInvoiceShippingTextView.setText(shippingAddress.toString());
            mInvoiceShippingAddressLayout.addView(mInvoiceShippingTextView);
            mInvoiceShippingAddressImageButton.setBackgroundResource(R.drawable.ic_edit);
            mInvoiceShippingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toShippingAddress = new Intent(getApplicationContext(), ChangeInvoiceShippingActivity.class);
                    toShippingAddress.putExtra("SHIPPINGADDRESS", (Serializable) shippingAddress);
                    toShippingAddress.putExtra("CHECK", shippingChecker);
                    startActivity(toShippingAddress);
                }
            });
        } else if (shippingAddress == null){
            shippingChecker = false;
            mInvoiceShippingTextView.setText("Dit adres is nog niet ingesteld.");
            mInvoiceShippingAddressImageButton.setBackgroundResource(R.drawable.ic_plus);
            mInvoiceShippingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toShippingAddress = new Intent(getApplicationContext(), ChangeInvoiceShippingActivity.class);
                    toShippingAddress.putExtra("CHECK", shippingChecker);
                    startActivity(toShippingAddress);
                }
            });
        }


    }
    }
