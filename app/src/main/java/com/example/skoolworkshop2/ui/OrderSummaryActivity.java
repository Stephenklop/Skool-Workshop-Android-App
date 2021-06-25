package com.example.skoolworkshop2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.payment.MollieDAOFactory;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.Bank;
import com.example.skoolworkshop2.domain.BillingAddress;
import com.example.skoolworkshop2.domain.Customer;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Payment;
import com.example.skoolworkshop2.domain.PaymentMethod;
import com.example.skoolworkshop2.domain.ShippingAddress;
import com.example.skoolworkshop2.logic.managers.localDb.UserManager;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {
    private APIDAOFactory mApiDAOFactory;
    private MollieDAOFactory mMollieDAOFactory;
    private UserManager mUserManager;
    private Customer mCustomer;
    private Order mOrder;
    private PaymentMethod selectedPaymentMethod;
    private ShippingAddress mShippingAddress;
    private BillingAddress mBillingAddress;
    private int mAmountOfItems;
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
    private boolean mIsIdeal;
    private Button mOrderButton;
    private ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        initializeAttributes();

        if(NetworkUtil.checkInternet(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        mSummaryNameTextView.setText(mCustomer.getFirstName() + " " + mCustomer.getLastName());
        mShippingAddressTextView.setText(mShippingAddress.getAddress());
        mShippingPostalCityTextView.setText(mShippingAddress.getPostcode() + " " + mShippingAddress.getCity());

        mBillingAddressTextView.setText(mBillingAddress.getAddress());
        mBillingPostalCityTextView.setText(mBillingAddress.getPostcode() + " " + mBillingAddress.getCity());

        mSubTotalPriceTitleTextView.setText("Subtotaal (" + mAmountOfItems + "):" );
        mSubTotalPriceTextView.setText("€" + String.format("%.2f", mOrder.getOrderPrice()).replace(".", ","));

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
            if (selectedPaymentMethod != null) {
                LocalDb.getDatabase(getBaseContext()).getOrderDAO().updateOrderPaymentMethod(selectedPaymentMethod.getFields().get(0), selectedPaymentMethod.getFields().get(1));

                switch (selectedPaymentMethod) {
                    case IDEAL:
                        enableLoadingIndicator();
                        mOrderButton.setEnabled(false);
                        Bank selectedBank = (Bank) mIdealSpinner.getSelectedItem();

                        // Send to Mollie
                        new Thread(() -> {
                            // Add order to Skool Workshop Database and save order to get the order id
                            Order order = mApiDAOFactory.getOrderDAO().addOrder(LocalDb.getDatabase(getBaseContext()).getOrderDAO().getOrder());

                            // Delete all payments
                            LocalDb.getDatabase(getBaseContext()).getPaymentDAO().deletePayment();

                            // Send mollie API request
                            Payment payment = mMollieDAOFactory.getPaymentDAO().addPayment(order.getId(), String.format("%.2f", order.getPrice()), "TEST ORDER", selectedBank);

                            if (payment != null) {
                                // Save payment locally
                                LocalDb.getDatabase(getBaseContext()).getPaymentDAO().addPayment(payment);

                                // Redirect to bank
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(payment.getCheckoutUrl()));
                                startActivity(browserIntent);
                            }
                        }).start();

                        break;

                    default:
                        enableLoadingIndicator();
                        mOrderButton.setEnabled(false);
                        new Thread(() -> {
                            // Add order to Skool Workshop Database and save order to get the order id
                            Order order = mApiDAOFactory.getOrderDAO().addOrder(LocalDb.getDatabase(getBaseContext()).getOrderDAO().getOrder());

                            // If there was a points coupon applied, remove the points from the account
                            if (LocalDb.getDatabase(getBaseContext()).getCouponDAO().getPointsCoupon() != null) {
                                mApiDAOFactory.getUserDAO().deleteUserPoints(order.getId());
                            }
                        });

                        // Clear shopping cart
                        LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().deleteEverythingFromShoppingCart();

                        Intent intent = new Intent(this, MollieResultActivity.class);
                        intent.putExtra("success", true);
                        startActivity(intent);

                        break;
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
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
                selectedPaymentMethod = PaymentMethod.BACS;
                break;
            case R.id.activity_summary_btn_cjp:
                selectPayment(mPaymentCjpButton);
                selectedPaymentMethod = PaymentMethod.CHECQUE;
                break;
            case R.id.activity_summary_btn_cash:
                selectPayment(mPaymentCashButton);
                selectedPaymentMethod = PaymentMethod.CHECQUE;
                break;
            case R.id.activity_summary_btn_ideal:
                selectPayment(mPaymentIdealButton);
                selectedPaymentMethod = PaymentMethod.IDEAL;
                break;
        }
    }

    private void initializeAttributes() {
        mApiDAOFactory = new APIDAOFactory();
        mMollieDAOFactory = new MollieDAOFactory();
        mUserManager = new UserManager(getApplication());
        mCustomer = mUserManager.getCustomer();
        mBackButton = findViewById(R.id.activity_summary_btn_back);
        mOrder = LocalDb.getDatabase(getBaseContext()).getOrderDAO().getOrder();
        mShippingAddress = LocalDb.getDatabase(getBaseContext()).getShippingAddressDAO().getShippingAddress();
        mBillingAddress = LocalDb.getDatabase(getBaseContext()).getBillingAddressDAO().getBillingAddress();
        mAmountOfItems = LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().getAmountOfShoppingCartItems();
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

    private void enableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backGround = findViewById(R.id.activity_login_loading_background);
        backGround.setVisibility(View.VISIBLE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                avd.start();
            }
        });
        loadingAlert.setAlpha(0);
        loadingAlert.setVisibility(View.VISIBLE);
        loadingAlert.animate().alpha(1).setDuration(200).start();
        avd.start();
    }

    private void disableLoadingIndicator() {
        LinearLayout loadingAlert = findViewById(R.id.activity_login_ll_loading_alert);
        ImageView loadingIndicator = findViewById(R.id.activity_login_img_loading_indicator);
        View backGround = findViewById(R.id.activity_login_loading_background);
        backGround.setVisibility(View.GONE);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) loadingIndicator.getDrawable();
        loadingAlert.setAlpha(1);
        loadingAlert.animate().alpha(0).setDuration(200).withEndAction(() ->
                loadingIndicator.setVisibility(View.GONE)
        ).start();
        avd.stop();
    }
}