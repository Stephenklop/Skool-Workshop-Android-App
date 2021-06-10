package com.example.skoolworkshop2.ui.workshop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;

import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.ui.CategoryAdapter;
import com.example.skoolworkshop2.R;
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
    private LocalAppStorage localAppStorage;
    private List<Product> mWorkshops;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        View root = findViewById(R.id.activity_workshops);

        MenuController menuController = new MenuController(root);
        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(1).setChecked(true);

        localAppStorage = new LocalAppStorage(getBaseContext());
        mWorkshops = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByType("Workshop");




        // Radiobutton

        // RecyclerView for whole activity
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_workshop_workshops);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWorkshopAdapter = new WorkshopAdapter((ArrayList<Product>) mWorkshops, this, getBaseContext());
        mRecyclerView.setAdapter(mWorkshopAdapter);

        CategoryAdapter ca = new CategoryAdapter(root ,this, WorkshopActivity.this, new CategoryAdapter.Listener() {
            @Override
            public void onChange(String filterLabel) {
                List<Product> workshops = new ArrayList<>();

                if (filterLabel.equals("Meest gekozen") || filterLabel.equals("Alles")) {
                    mWorkshopAdapter.setWorkshopList(mWorkshops);
                } else {
                    for (Product workshop : mWorkshops) {
                        if(workshop.getCategory().toString().equals(filterLabel)){
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
                mWorkshopAdapter.getFilter().filter(s);
                return false;
            }
        });

        mWorkshopAdapter.notifyDataSetChanged();
    }


    @Override
    public void onWorkshopSelected(int position) {

    }

}