package com.example.skoolworkshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.skoolworkshop2.domain.Workshop;
import java.util.ArrayList;

public class WorkshopActivity extends AppCompatActivity implements WorkshopAdapter.OnWorkshopSelectionListener, CategoryAdapter.OnCategorySelectionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private WorkshopAdapter mWorkshopAdapter;
    private CategoryAdapter mCategoryAdapter;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mRecyclerView;
    private ArrayList<Workshop> mWorkshops = new ArrayList<>();
    private ArrayList<String> mCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWorkshops.add(new Workshop(1, "Test", "Test", 55.55, "11-11-2021", 1, "Dans"));
        mWorkshops.add(new Workshop(1, "Test", "Test", 55.55, "11-11-2021", 1, "Beeldende Kunst"));
        mWorkshops.add(new Workshop(1, "Result", "Test", 55.55, "11-11-2021", 1, "Muziek"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        // RecyclerView for whole activity
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_workshops_txt_category_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWorkshopAdapter = new WorkshopAdapter(mWorkshops, this);
        mRecyclerView.setAdapter(mWorkshopAdapter);
        // RecyclerView for categories
        mCategoryRecyclerView = (RecyclerView) findViewById(R.id.activity_workshops_sv_categories);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mCategoryAdapter = new CategoryAdapter(mCategories, this);
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        // SearchView
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
    public void onCategorySelected(int position) {
        String category = mCategories.get(position);
        if (category.equals("Meest gekozen")) {
            mWorkshopAdapter = new WorkshopAdapter(mWorkshops, this);
            mRecyclerView.setAdapter(mWorkshopAdapter);
        } else {
            Log.d(LOG_TAG, "onCategorySelected: category: " + category);
            ArrayList<Workshop> workshops = new ArrayList<>();
            for (Workshop workshop : mWorkshops) {
                if (workshop.getCategory().equals(category)) {
                    workshops.add(workshop);
                }
            }
            Log.d(LOG_TAG, "onCategorySelected: size: " + workshops.size());
            mRecyclerView.setAdapter(null);
            mWorkshopAdapter = new WorkshopAdapter(workshops, this);
            mRecyclerView.setAdapter(mWorkshopAdapter);
        }
    }
}