package com.example.skoolworkshop2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.payment.MollieDAOFactory;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {
    private MollieDAOFactory mMollieDAOFactory;
    private UserManager mUserManager;
    private Customer mCustomer;
    private Order mOrder;
    private ShippingAddress mShippingAddress;
    private BillingAddress mBillingAddress;
    private int mAmountOfItems;
    private double mSubTotalPrice;
    private double mTravelExpenses;
    private LinearLayout mSummaryLinearLayout;
    private TextView mSummaryNameTextView;
    private TextView mShippingAddressTextView;
    private TextView mShippingPostalCityTextView;
    private TextView mBillingAddressTextView;
    private TextView mBillingPostalCityTextView;
    private TextView mSubTotalPriceTitleTextView;
    private TextView mSubTotalPriceTextView;
    private TextView mTravelExpensesTextView;
    private TextView mTotalPriceTextView;
    private LinearLayout mPaymentMethodsLinearLayout;
    private LinearLayout mPaymentTransferButton;
    private LinearLayout mPaymentCjpButton;
    private LinearLayout mPaymentCashButton;
    private LinearLayout mPaymentIdealButton;
    private Spinner mIdealSpinner;
    private Button mOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        initializeAttributes();

        if(NetworkUtil.checkInternet(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        mSummaryNameTextView.setText(mCustomer.getFirstName() + " " + mCustomer.getFirstName());
        mShippingAddressTextView.setText(mShippingAddress.getAddress());
        mShippingPostalCityTextView.setText(mShippingAddress.getPostcode() + " " + mShippingAddress.getCity());

        mBillingAddressTextView.setText(mBillingAddress.getAddress());
        mBillingPostalCityTextView.setText(mBillingAddress.getPostcode() + " " + mBillingAddress.getCity());

        mSubTotalPriceTitleTextView.setText("Subtotaal (" + mAmountOfItems + "):" );
        mSubTotalPriceTextView.setText("€" + String.format("%.2f", mSubTotalPrice).replace(".", ","));

        mTravelExpensesTextView.setText("€" + String.format("%.2f", mOrder.getTravelCosts()).replace(".", ","));
        mTotalPriceTextView.setText("€" + String.format("%.2f", mOrder.getPrice()).replace(".", ","));

        new Thread(() -> {
            mIdealSpinner.setAdapter(new BankArrayAdapter(getBaseContext(), mMollieDAOFactory.getBankDAO().getAllBanks()));
        }).start();

        mPaymentTransferButton.setOnClickListener(this);
        mPaymentCjpButton.setOnClickListener(this);
        mPaymentCashButton.setOnClickListener(this);
        mPaymentIdealButton.setOnClickListener(this);

        mOrderButton.setText("Door naar betalen");
        mOrderButton.setOnClickListener(v -> {
            System.out.println(mOrder);
        });
    }

    @Override
    public void onClick (View v) {
        deselectPayment(mPaymentTransferButton);
        deselectPayment(mPaymentCjpButton);
        deselectPayment(mPaymentCashButton);
        deselectPayment(mPaymentIdealButton);

        switch (v.getId()) {
            case R.id.activity_summary_btn_transfer:
                selectPayment(mPaymentTransferButton);
                break;
            case R.id.activity_summary_btn_cjp:
                selectPayment(mPaymentCjpButton);
                break;
            case R.id.activity_summary_btn_cash:
                selectPayment(mPaymentCashButton);
                break;
            case R.id.activity_summary_btn_ideal:
                selectPayment(mPaymentIdealButton);
                break;
        }
    }

    private void initializeAttributes() {
        mMollieDAOFactory = new MollieDAOFactory();
        mUserManager = new UserManager(getApplication());
        mCustomer = mUserManager.getCustomer();
        mOrder = LocalDb.getDatabase(getBaseContext()).getOrderDAO().getOrder();
        mShippingAddress = LocalDb.getDatabase(getBaseContext()).getShippingAddressDAO().getShippingAddress();
        mBillingAddress = LocalDb.getDatabase(getBaseContext()).getBillingAddressDAO().getBillingAddress();
        mAmountOfItems = LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().getAmountOfShoppingCartItems();
        mSubTotalPrice = LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().getTotalShoppingCartPrice();
        mTravelExpenses = 0;
        mSummaryLinearLayout = findViewById(R.id.activity_summary_overview);
        mSummaryNameTextView = mSummaryLinearLayout.findViewById(R.id.comonent_summary_name);
        mShippingAddressTextView = mSummaryLinearLayout.findViewById(R.id.comonent_summary_street_housenr);
        mShippingPostalCityTextView = mSummaryLinearLayout.findViewById(R.id.comonent_summary_postalcode_place);
        mBillingAddressTextView = mSummaryLinearLayout.findViewById(R.id.comonent_summary_invoice_street_housenr);
        mBillingPostalCityTextView = mSummaryLinearLayout.findViewById(R.id.comonent_summary_invoice_postalcode_place);
        mSubTotalPriceTitleTextView = findViewById(R.id.activity_summary_tv_subtotal_key);
        mSubTotalPriceTextView = findViewById(R.id.activity_summary_tv_subtotal_value);
        mTravelExpensesTextView = findViewById(R.id.activity_summary_tv_travel_cost_value);
        mTotalPriceTextView = findViewById(R.id.activity_summary_tv_total_value);
        mPaymentMethodsLinearLayout = findViewById(R.id.activity_summary_ll_payment_methods);
        mPaymentTransferButton = findViewById(R.id.activity_summary_btn_transfer);
        mPaymentCjpButton = findViewById(R.id.activity_summary_btn_cjp);
        mPaymentCashButton = findViewById(R.id.activity_summary_btn_cash);
        mPaymentIdealButton = findViewById(R.id.activity_summary_btn_ideal);
        mIdealSpinner = findViewById(R.id.component_btn_payment_spnr_ideal);
        mOrderButton = findViewById(R.id.activity_summary_btn_confirm);
    }

    private void deselectPayment (View v) {
        TextView mPaymentMethodTv = v.findViewById(R.id.component_btn_payment_tv_method);
        LinearLayout mPaymentExpandLl = v.findViewById(R.id.component_btn_payment_ll_expand);
        v.setSelected(false);
        mPaymentMethodTv.setTextColor(getColor(R.color.black));
        mPaymentExpandLl.setVisibility(View.GONE);
    }

    private void selectPayment (View v) {
        TextView mPaymentMethodTv = v.findViewById(R.id.component_btn_payment_tv_method);
        LinearLayout mPaymentExpandLl = v.findViewById(R.id.component_btn_payment_ll_expand);
        v.setSelected(true);
        mPaymentMethodTv.setTextColor(getColor(R.color.white));
        mPaymentExpandLl.setVisibility(View.VISIBLE);
    }
}