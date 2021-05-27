package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class WorkshopActivity extends AppCompatActivity implements WorkshopAdapter.OnWorkshopSelectionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private WorkshopAdapter mWorkshopAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Workshop> mWorkshops = new ArrayList<>();
    private RecyclerView mCategoryRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_workshops_txt_category_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWorkshopAdapter = new WorkshopAdapter(mWorkshops, this);
        mRecyclerView.setAdapter(mWorkshopAdapter);
        SearchView searchView = (SearchView) findViewById(R.id.activity_workshops_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mWorkshopAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


    @Override
    public void onWorkshopSelected(int position) {

    }

    @Override
    public void onWorkshopCategorySelected(int position) {

    }


}