package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.LocalAppStorage;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.CultureDay;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.cultureDay.CulturedayActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Workshop> workshopArrayList;
    private LocalAppStorage localAppStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View root = (View) findViewById(R.id.activity_home);

        String[] shopDesc = {"Desc 1", "Desc 2", "Desc 3", "Desc 4"};

        localAppStorage = new LocalAppStorage(getApplicationContext());
        localAppStorage.deleteKey("cartItems");
        localAppStorage.addToList("cartItems", new Workshop(1, "Workshop 1", shopDesc, 10.011, "Today", 10, Category.DS));
        localAppStorage.addToList("cartItems", new Workshop(2, "Workshop 2", shopDesc, 121.39, "Today", 10, Category.DS));
        localAppStorage.addToList("cartItems", new Workshop(3, "Workshop 3", shopDesc, 10.99, "Today", 10, Category.DS));
        localAppStorage.addToList("cartItems", new Workshop(4, "Workshop 4", shopDesc, 92.12, "Today", 10, Category.DS));

        MenuController mc = new MenuController(root);
        this.workshopArrayList = new ArrayList<>();
        String[] desc = {"blabla", "test", "info", "price"};
        workshopArrayList.add(new Workshop(1, "Graffiti", desc,55.55, "Test", 60, Category.DS));
        workshopArrayList.add(new Workshop(2, "T-shirt Ontwerpen", desc,55.55, "Test", 60, Category.BK));
        workshopArrayList.add(new Workshop(3, "Test", desc,55.55, "Test", 60, Category.MA));

        View searchPage = findViewById(R.id.activity_home_item_reservation);
        searchPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mc.sendToSearch();
            }
        });

        View cultureDay = findViewById(R.id.activity_home_item_account);
        cultureDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CulturedayActivity.class);
                intent.putExtra("Cultureday", new CultureDay(1, "Cultureday", new String[]{"String", "Description", "Info", "Price"}, workshopArrayList, 4, 1650,"5/28/2021", 100));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });


    }

}