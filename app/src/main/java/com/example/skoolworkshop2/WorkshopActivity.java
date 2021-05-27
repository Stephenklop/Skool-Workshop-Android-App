package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopDetailActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        Intent intent = new Intent(this, WorkshopDetailActivity.class);

        intent.putExtra("Workshop", mWorkshops.get(position));
        startActivity(intent);

    }
}