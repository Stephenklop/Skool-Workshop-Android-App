package com.example.skoolworkshop2.ui.shoppingCart;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.ShoppingCartItem;
import com.example.skoolworkshop2.domain.ProductItem;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.AddressInfoActivity;
import com.example.skoolworkshop2.ui.AddressInfoLayoutTestActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        View root = (View) findViewById(R.id.activity_shopping_cart);
        menuController = new MenuController(root);

        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(3).setChecked(true);

        shoppingCartItems = LocalDb.getDatabase(getBaseContext()).getShoppingCartDAO().getItemsInShoppingCart();

        shoppingCartRecyclerView = findViewById(R.id.activity_shopping_cart_rv);

        mAdapter = new ShoppingCartRecyclerViewAdapter(shoppingCartItems, ShoppingCartActivity.this);

        shoppingCartRecyclerView.setAdapter(mAdapter);

        totalPriceTitleTextView = findViewById(R.id.activity_shopping_cart_tv_total_cost_key);
        totalPriceTitleTextView.setText("Totaal (" + shoppingCartItems.size() + ")");

        totalPriceTextView = findViewById(R.id.activity_shopping_cart_tv_total_cost_value);
        // TODO: Add price
        totalPriceTextView.setText("€" + String.format("%.2f", calculateTotalPrice()).replace(".", ","));

        orderButton = findViewById(R.id.activity_shopping_cart_btn_confirm);
        orderButton.setText("Verder met bestellen");
        orderButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddressInfoActivity.class);
            startActivity(intent);
        });
    }

    private double calculateTotalPrice() {
        double total = 0;

        for (ShoppingCartItem product : shoppingCartItems) {
            total += product.getTotalPrice();
        }

        return total;
    }
}
