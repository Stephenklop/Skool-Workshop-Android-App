package com.example.skoolworkshop2.ui.shoppingCart;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.DAOFactory;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.dao.skoolWorkshopApi.APIDAOFactory;
import com.example.skoolworkshop2.domain.Coupon;
import com.example.skoolworkshop2.domain.ProductItem;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.AddressInfoActivity;
import com.example.skoolworkshop2.ui.AddressInfoLayoutTestActivity;
import com.example.skoolworkshop2.ui.RoundedDialog;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        mPromoAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPromoAddBtn.setEnabled(false);
                DAOFactory factory = new APIDAOFactory();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(factory.getCouponDAO().checkCoupon(mPromoEt.getText().toString())){
                            coupon = factory.getCouponDAO().getCoupon(mPromoEt.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPromoAddBtn.setEnabled(true);
                                    mPromoEt.setText("");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new RoundedDialog(ShoppingCartActivity.this, "Ongeldige coupon", "De coupon die ingevuld is bestaat niet of is niet meer geldig.");
                                    mPromoAddBtn.setEnabled(true);
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

        for (ShoppingCartItem product : shoppingCartItems) {
            total += product.getTotalPrice();
        }

        return total;
    }
}
