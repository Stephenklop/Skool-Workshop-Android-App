package com.example.skoolworkshop2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.example.skoolworkshop2.domain.Category;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class WorkshopActivity extends AppCompatActivity implements WorkshopAdapter.OnWorkshopSelectionListener{

    private final String LOG_TAG = this.getClass().getSimpleName();
    private WorkshopAdapter mWorkshopAdapter;
    private CategoryAdapter mCategoryAdapter;
    private RecyclerView mCategoryRecyclerView;
    private RadioButton radioButton;
    private RecyclerView mRecyclerView;
    private ArrayList<Workshop> mWorkshops = new ArrayList<>();
    private ArrayList<String> mCategories = new ArrayList<>();
    private ArrayList<String> mEnumCategories = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        View root = findViewById(R.id.activity_workshops);

        MenuController mc = new MenuController(root);

        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(1).setChecked(true);
        // Add data to workshops
        mWorkshops.add(new Workshop(1, "Test", new String[]{"Test", "test", "test", "test"}, 55.55, "11-11-2021", 1, Category.DS));
        mWorkshops.add(new Workshop(1, "Test", new String[]{"Test", "test", "test", "test"}, 55.55, "11-11-2021", 1, Category.BK));
        mWorkshops.add(new Workshop(1, "Result", new String[]{"Test", "test", "test", "test"}, 55.55, "11-11-2021", 1, Category.MK));
        // Add enum list with data
        mEnumCategories.addAll(addCategories());
        // Radiobutton

        // RecyclerView for whole activity
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_workshop_workshops);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWorkshopAdapter = new WorkshopAdapter(mWorkshops, this);
        mRecyclerView.setAdapter(mWorkshopAdapter);

        CategoryAdapter ca = new CategoryAdapter(root, WorkshopActivity.this, new CategoryAdapter.Listener() {
            @Override
            public void onChange(String filterLabel) {
                List<Workshop> workshops = new ArrayList<>();

                if (filterLabel.equals("Meest gekozen")) {
                    mWorkshopAdapter.setWorkshopList(mWorkshops);
                } else {
                    for (Workshop workshop : mWorkshops) {
                        if (workshop.getCategory().label.equals(filterLabel)) {
                            workshops.add(workshop);
                        }
                    }

                    mWorkshopAdapter.setWorkshopList(workshops);
                }
            }
        });

        // SearchView
        SearchView searchView = (SearchView) findViewById(R.id.activity_workshops_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Workshop> filter = new ArrayList<>();
                for (Workshop workshop : mWorkshops){
                    if(workshop.getName().toLowerCase().contains(s)){
                        filter.add(workshop);
                    }
                }
                if(s.equals("")){
                    mWorkshopAdapter.setWorkshopList(mWorkshops);
                } else {
                    mWorkshopAdapter.setWorkshopList(filter);
                }
                return false;
            }
        });

    }


    // Hard-coded categories
    public ArrayList<String> addCategories(){
        ArrayList<String> list = new ArrayList<>();
        mEnumCategories.add("Meest gekozen");
        mEnumCategories.add("BK");
        mEnumCategories.add("DS");
        mEnumCategories.add("MA");
        mEnumCategories.add("MK");
        mEnumCategories.add("ST");
        mEnumCategories.add("TR");
        return list;
    }

    @Override
    public void onWorkshopSelected(int position) {

    }

}