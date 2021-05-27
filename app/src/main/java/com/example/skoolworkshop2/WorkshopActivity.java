package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;

public class WorkshopActivity extends AppCompatActivity implements WorkshopAdapter.OnWorkshopSelectionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private WorkshopAdapter mWorkshopAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Workshop> mWorkshops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        View root = findViewById(R.id.activity_workshops);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_workshops_txt_category_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWorkshopAdapter = new WorkshopAdapter(mWorkshops, this);
        mRecyclerView.setAdapter(mWorkshopAdapter);

        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(1).setChecked(true);

        MenuController mc = new MenuController(root);
    }


    @Override
    public void onWorkshopSelected(int position) {
        String name = mWorkshops.get(position).getName();
        String description = mWorkshops.get(position).getDescription();
        Double price = mWorkshops.get(position).getPrice();
        int maxParticipant = mWorkshops.get(position).getMaxParticipants();

        Intent intent = new Intent(this, WorkshopDetailActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("PRICE", price);
        intent.putExtra("MAXP", maxParticipant);
        startActivity(intent);

    }
}