package com.example.skoolworkshop2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.WorkshopActivity;
import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.CultureDay;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.menuController.MenuController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Workshop> workshopArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View root = (View) findViewById(R.id.activity_home);

        MenuController mc = new MenuController(root);
        this.workshopArrayList = new ArrayList<>();
        String[] desc = {"blabla", "test", "info", "price"};
        workshopArrayList.add(new Workshop(1, "Test", desc,55.55, "Test", 60, Category.DS));
        workshopArrayList.add(new Workshop(2, "Test", desc,55.55, "Test", 60, Category.BK));
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