package com.example.skoolworkshop2.ui.User;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.ui.AddressInfoLayoutTestActivity;

import java.util.ArrayList;
import java.util.List;

public class InvoiceAdressActivity extends AppCompatActivity {

    // BillingAddress
    private ConstraintLayout mInvoiceAddressLayout;
    private ImageButton mInvoiceBillingAddressImageButton;
    private BillingAddress billingAddress;
    private TextView mInvoiceBillingTextView;
    // BillingAddress
    private ConstraintLayout mInvoiceShippingAddressLayout;
    private ImageButton mInvoiceShippingAddressImageButton;
    private ShippingAddress shippingAddress;
    private TextView mInvoiceShippingTextView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_data);

        // Billing
        mInvoiceAddressLayout = findViewById(R.id.activity_invoice_data_item_invoice_address);
        mInvoiceBillingAddressImageButton = mInvoiceAddressLayout.findViewById(R.id.component_invoice_data_btn_edit);
        mInvoiceBillingTextView = mInvoiceAddressLayout.findViewById(R.id.component_invoice_data_tv_data);
        // Shipping
        mInvoiceShippingAddressLayout = findViewById(R.id.activity_invoice_data_item_workshop_location);
        mInvoiceShippingAddressImageButton = mInvoiceShippingAddressLayout.findViewById(R.id.component_invoice_data_btn_edit);
        mInvoiceBillingTextView = mInvoiceAddressLayout.findViewById(R.id.component_invoice_data_tv_data);

        // Usermanager
        com.example.skoolworkshop2.logic.managers.localDb.UserManager iem = new UserManager(this.getApplication());
        // Loading billing addresses
        billingAddress = iem.getInfo().getBillingAddress();
        // Loading shipping adresses
        shippingAddress = iem.getInfo().getShippingAddress();

        if (billingAddress != null){
            mInvoiceBillingTextView.setText(billingAddress.toString());
            mInvoiceAddressLayout.addView(mInvoiceBillingTextView);
            mInvoiceBillingAddressImageButton.setBackgroundResource(R.drawable.ic_edit);

            mInvoiceBillingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBillingaddress = new Intent(getApplicationContext(), AddressInfoLayoutTestActivity.class);
                    toBillingaddress.putExtra("BILLINGADDRESS", (Parcelable) billingAddress);
                    startActivity(toBillingaddress);
                }
            });
        } else {
            mInvoiceBillingAddressImageButton.setBackgroundResource(R.drawable.ic_plus);
            mInvoiceBillingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), AddressInfoLayoutTestActivity.class));
                }
            });
        }

        if(shippingAddress != null){
            mInvoiceShippingTextView.setText(shippingAddress.toString());
            mInvoiceShippingAddressLayout.addView(mInvoiceShippingTextView);
            mInvoiceShippingAddressImageButton.setBackgroundResource(R.drawable.ic_edit);
            mInvoiceShippingAddressImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }
    }
