package com.example.skoolworkshop2.ui.shoppingCart;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.LocalAppStorage;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ShoppingCartActivity extends AppCompatActivity {
    private RecyclerView shoppingCartRecyclerView;
    private ShoppingCartRecyclerViewAdapter mAdapter;
    private LocalAppStorage localAppStorage;
    private MenuController menuController;
    private List<Product> shoppingCartItems;
    private TextView totalPriceTitleTextView;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        View root = (View) findViewById(R.id.activity_shopping_cart);
        localAppStorage = new LocalAppStorage(getBaseContext());
        menuController = new MenuController(root);

        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(3).setChecked(true);

        // Read the shopping cart items.
        // If there are no shopping cart items an empty ArrayList is initialized.
        shoppingCartItems = localAppStorage.getList("cartItems");

        shoppingCartRecyclerView = findViewById(R.id.activity_shopping_cart_rv);

        mAdapter = new ShoppingCartRecyclerViewAdapter(shoppingCartItems, ShoppingCartActivity.this);

        shoppingCartRecyclerView.setAdapter(mAdapter);

        totalPriceTitleTextView = findViewById(R.id.activity_shopping_cart_tv_total_cost_key);
        totalPriceTitleTextView.setText("Totaal (" + shoppingCartItems.size() + ")");

        totalPriceTextView = findViewById(R.id.activity_shopping_cart_tv_total_cost_value);
        totalPriceTextView.setText("€ " + String.format("%.2f", calculateTotalPrice()).replace(".", ","));
    }

    private double calculateTotalPrice() {
        double total = 0;

        for (Product product : shoppingCartItems) {
            total += product.getPrice();
        }

        return total;
    }
}
