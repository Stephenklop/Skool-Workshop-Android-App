package com.example.skoolworkshop2.ui.shoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.Coupon;
import com.example.skoolworkshop2.domain.DiscountType;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.AddressInfoActivity;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    private RecyclerView shoppingCartRecyclerView;
    private ShoppingCartRecyclerViewAdapter mAdapter;
    private MenuController menuController;
    private List<ShoppingCartItem> shoppingCartItems;
    private TextView totalPriceTitleTextView;
    private TextView totalPriceTextView;
    private Button orderButton;
    private ConstraintLayout mPromoCl;
    private EditText mPromoEt;
    private Button mPromoAddBtn;
    private TextView mCouponsTvPercent;
    private TextView mCouponsWorthTvPercent;
    private ImageView mcouponsIgPercent;
    private TextView mCouponsTvProduct;
    private TextView mCouponsWorthTvProduct;
    private ImageView mCouponsIgProduct;
    private TextView mCouponsTvCart;
    private TextView mCouponsWorthTvCart;
    private ImageView mCouponsIgCart;
    private TextView mCouponsTvPoints;
    private TextView mCouponsWorthTvPoints;
    private ImageView mCouponsIgPoints;
    private View pointsAddView;
    private TextView mPointsTv;
    private Button mPointsAddButton;

    private Coupon coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        View root = (View) findViewById(R.id.activity_shopping_cart);
        menuController = new MenuController(root);

        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        mPromoCl = findViewById(R.id.activity_shopping_cart_item_promo);
        mPromoEt = mPromoCl.findViewById(R.id.component_promo_et_txt);
        mPromoAddBtn = mPromoCl.findViewById(R.id.component_promo_btn_add);



        mCouponsTvPercent = findViewById(R.id.activity_shopping_cart_tv_coupons_percent);
        mCouponsWorthTvPercent = findViewById(R.id.activity_shopping_cart_tv_coupons_percent_price);
        mcouponsIgPercent= findViewById(R.id.activity_shopping_cart_ig_coupons_percent);

        mCouponsTvPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.PROCENTKORTING);
                mCouponsTvPercent.setVisibility(View.GONE);
                mCouponsWorthTvPercent.setVisibility(View.GONE);
                mcouponsIgPercent.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mcouponsIgPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.PROCENTKORTING);
                mCouponsTvPercent.setVisibility(View.GONE);
                mCouponsWorthTvPercent.setVisibility(View.GONE);
                mcouponsIgPercent.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mCouponsTvCart = findViewById(R.id.activity_shopping_cart_tv_coupons_cart);
        mCouponsWorthTvCart = findViewById(R.id.activity_shopping_cart_tv_coupons_cart_price);
        mCouponsIgCart = findViewById(R.id.activity_shopping_cart_ig_coupons_cart);

        mCouponsTvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.VASTEKORTING);
                mCouponsTvCart.setVisibility(View.GONE);
                mCouponsWorthTvCart.setVisibility(View.GONE);
                mCouponsIgCart.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mCouponsIgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.VASTEKORTING);
                mCouponsTvCart.setVisibility(View.GONE);
                mCouponsWorthTvCart.setVisibility(View.GONE);
                mCouponsIgCart.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mCouponsTvProduct = findViewById(R.id.activity_shopping_cart_tv_coupons_product);
        mCouponsWorthTvProduct = findViewById(R.id.activity_shopping_cart_tv_coupons_product_price);
        mCouponsIgProduct = findViewById(R.id.activity_shopping_cart_ig_coupons_product);

        mCouponsTvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.PRODUCTKORTING);
                mCouponsTvProduct.setVisibility(View.GONE);
                mCouponsWorthTvProduct.setVisibility(View.GONE);
                mCouponsIgProduct.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mCouponsIgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.PRODUCTKORTING);
                mCouponsTvProduct.setVisibility(View.GONE);
                mCouponsWorthTvProduct.setVisibility(View.GONE);
                mCouponsIgProduct.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mCouponsTvPoints = findViewById(R.id.activity_shopping_cart_tv_points);
        mCouponsWorthTvPoints = findViewById(R.id.activity_shopping_cart_tv_points_price);
        mCouponsIgPoints = findViewById(R.id.activity_shopping_cart_ig_points);

        mCouponsTvPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.POINTS);
                mCouponsTvPoints.setVisibility(View.GONE);
                mCouponsWorthTvPoints.setVisibility(View.GONE);
                mCouponsIgPoints.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        mCouponsIgPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCouponCategory(DiscountType.POINTS);
                mCouponsTvPoints.setVisibility(View.GONE);
                mCouponsWorthTvPoints.setVisibility(View.GONE);
                mCouponsIgPoints.setVisibility(View.GONE);
                calculateTotalPrice();
            }
        });

        pointsAddView = findViewById(R.id.activity_shopping_cart_item_points);
        mPointsTv = pointsAddView.findViewById(R.id.component_promo_et_txt);
        mPointsAddButton = pointsAddView.findViewById(R.id.component_promo_btn_add);


        if(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getPoints() >= 1500 && LocalDb.getDatabase(getApplication()).getCouponDAO().getPointsCoupon() == null){
            pointsAddView.setVisibility(View.VISIBLE);
            int points = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getPoints();
            mPointsTv.setText( points + " punten (€" + String.format("%.2f" ,points * 0.03).replace(".", ",") + ")");
            mPointsAddButton.setText("Voeg toe");
        }

        mPointsAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getPoints();
                mCouponsTvPoints.setVisibility(View.VISIBLE);
                mCouponsWorthTvPoints.setVisibility(View.VISIBLE);
                mCouponsTvPoints.setText("Punten: " + points);
                double money = points * 0.03;
                mCouponsWorthTvPoints.setText("-€" + (String.format("%.2f" , money).replace(".", ",")));
                pointsAddView.setVisibility(View.GONE);
                LocalDb.getDatabase(getApplication()).getCouponDAO().insertCoupon(new Coupon(1, "points", (points * 0.03), "points", ""));
                calculateTotalPrice();
            }
        });

        mPromoAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPromoAddBtn.setEnabled(false);
                DAOFactory factory = new APIDAOFactory();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(factory.getCouponDAO().checkCoupon(mPromoEt.getText().toString())){
                            try{
                                LocalDb.getDatabase(getApplication()).getCouponDAO().insertCoupon(factory.getCouponDAO().getCoupon(mPromoEt.getText().toString()));
                                calculateTotalPrice();
                            } catch (Exception e){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new RoundedDialog(ShoppingCartActivity.this, "Ongeldige coupon", "Het type coupon dat ingevuld is wordt al gebruikt. Je mag maximaal 1 coupon per type hebben.");
                                        mPromoAddBtn.setEnabled(true);
                                        mPromoEt.setText("");
                                        calculateTotalPrice();
                                    }
                                });
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPromoAddBtn.setEnabled(true);
                                    mPromoEt.setText("");
                                    calculateTotalPrice();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new RoundedDialog(ShoppingCartActivity.this, "Ongeldige coupon", "De coupon die ingevuld is bestaat niet of is niet meer geldig.");
                                    mPromoAddBtn.setEnabled(true);
                                    mPromoEt.setText("");
                                    calculateTotalPrice();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        menu.getMenu().getItem(3).setChecked(true);

        shoppingCartItems = LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().getItemsInShoppingCart();

        shoppingCartRecyclerView = findViewById(R.id.activity_shopping_cart_rv);

        mAdapter = new ShoppingCartRecyclerViewAdapter(shoppingCartItems, ShoppingCartActivity.this);

        shoppingCartRecyclerView.setAdapter(mAdapter);
        shoppingCartRecyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            totalPriceTextView.setText("€" + String.format("%.2f", calculateTotalPrice()).replace(".", ","));
            totalPriceTitleTextView.setText("Subtotaal (" + shoppingCartItems.size() + ")");

            if (shoppingCartItems.size() == 0) {
                orderButton.setEnabled(false);
            }
        });

        totalPriceTitleTextView = findViewById(R.id.activity_shopping_cart_tv_total_cost_key);
        totalPriceTitleTextView.setText("Subtotaal (" + shoppingCartItems.size() + ")");

        totalPriceTextView = findViewById(R.id.activity_shopping_cart_tv_total_cost_value);
        totalPriceTextView.setText("€" + String.format("%.2f", calculateTotalPrice()).replace(".", ","));

        orderButton = findViewById(R.id.activity_shopping_cart_btn_confirm);
        orderButton.setText("Verder met bestellen");
        orderButton.setOnClickListener(v -> {
            if (LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().getAmountOfShoppingCartItems() > 0) {
                Intent intent = new Intent(this, AddressInfoActivity.class);
                startActivity(intent);
            }
        });

        if (shoppingCartItems.size() == 0) {
            orderButton.setEnabled(false);
        }


        mPromoAddBtn.setText("Voeg toe");
    }

    private double calculateTotalPrice() {
        double total = 0;
        ArrayList<Coupon> coupons = (ArrayList<Coupon>) LocalDb.getDatabase(getApplication()).getCouponDAO().getAllCoupons();

        for (ShoppingCartItem product : shoppingCartItems) {
            total += product.getTotalPrice();
        }

        for (Coupon coupon : coupons){
            if(coupon.getDiscountTypeEnum() == DiscountType.PRODUCTKORTING){
                mCouponsTvProduct.setVisibility(View.VISIBLE);
                mCouponsWorthTvProduct.setVisibility(View.VISIBLE);
                mCouponsIgProduct.setVisibility(View.VISIBLE);

                total -= coupon.getAmount() * shoppingCartItems.size();
                mCouponsTvProduct.setText("Coupon: " + coupon.getCode());
                mCouponsWorthTvProduct.setText("-€" + String.format("%.2f" ,coupon.getAmount() * shoppingCartItems.size()).replace(".", ","));
            } else if(coupon.getDiscountTypeEnum() == DiscountType.VASTEKORTING){
                mCouponsTvCart.setVisibility(View.VISIBLE);
                mCouponsWorthTvCart.setVisibility(View.VISIBLE);
                mCouponsIgCart.setVisibility(View.VISIBLE);

                total -= coupon.getAmount();
                mCouponsTvCart.setText("Coupon: " + coupon.getCode());
                mCouponsWorthTvCart.setText("-€" + String.format("%.2f" ,coupon.getAmount()).replace(".", ","));
            } else if(coupon.getDiscountTypeEnum() == DiscountType.PROCENTKORTING){
                mCouponsTvPercent.setVisibility(View.VISIBLE);
                mCouponsWorthTvPercent.setVisibility(View.VISIBLE);
                mcouponsIgPercent.setVisibility(View.VISIBLE);

                double discount = total * ((100 - coupon.getAmount()) / 100);
                mCouponsWorthTvPercent.setText("-€" + String.format("%.2f" ,total - discount).replace(".", ","));
                total = discount;
                mCouponsTvPercent.setText("Coupon: " + coupon.getCode());
            } else if(coupon.getDiscountTypeEnum() == DiscountType.POINTS){
                if(LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getPoints() >= 1500){
                    LocalDb.getDatabase(getApplication()).getCouponDAO().deleteCoupon(1);
                    int points = LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getPoints();
                    LocalDb.getDatabase(getApplication()).getCouponDAO().insertCoupon(new Coupon(1, "points", (points * 0.03), "points", ""));

                    mCouponsTvPoints.setVisibility(View.VISIBLE);
                    mCouponsWorthTvPoints.setVisibility(View.VISIBLE);
                    mCouponsIgPoints.setVisibility(View.VISIBLE);

                    total -= coupon.getAmount();
                    mCouponsWorthTvPoints.setText("-€" + String.format("%.2f" ,coupon.getAmount()).replace(".", ","));
                    mCouponsTvPoints.setText("Punten: " + LocalDb.getDatabase(getApplication()).getUserDAO().getInfo().getPoints() + " punten");
                } else {
                    LocalDb.getDatabase(getApplication()).getCouponDAO().deleteCoupon(1);
                    mCouponsTvPoints.setVisibility(View.GONE);
                    mCouponsWorthTvPoints.setVisibility(View.GONE);
                    mCouponsIgPoints.setVisibility(View.GONE);
                }
            }
        }

        return total;
    }

    public void deleteCouponCategory(DiscountType type){
        ArrayList<Coupon> coupons = (ArrayList<Coupon>) LocalDb.getDatabase(getApplication()).getCouponDAO().getAllCoupons();
        for (Coupon coupon : coupons) {
            if(coupon.getDiscountTypeEnum() == type){
                LocalDb.getDatabase(getApplication()).getCouponDAO().deleteCoupon(coupon.getId());
                calculateTotalPrice();
            }
        }
    }
}
