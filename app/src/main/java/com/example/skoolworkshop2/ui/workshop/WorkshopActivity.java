package com.example.skoolworkshop2.ui.workshop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.logic.networkUtils.NetworkUtil;
import com.example.skoolworkshop2.ui.CategoryAdapter;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.example.skoolworkshop2.ui.SplashScreenActivity;
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
    private TextView mCategoryTitleTv;
    private LocalAppStorage localAppStorage;
    private List<Product> mWorkshops;

    private String searchValue = "";
    private String categorySelected = "Alles";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        View root = findViewById(R.id.activity_workshops);

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        if(NetworkUtil.checkInternet(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
        }

        MenuController menuController = new MenuController(root);
        BottomNavigationView menu = root.findViewById(R.id.activity_menu_buttons);
        menu.getMenu().getItem(1).setChecked(true);

        localAppStorage = new LocalAppStorage(getBaseContext());
        mWorkshops = LocalDb.getDatabase(getBaseContext()).getProductDAO().getAllProductsByType("Workshop");

        TextView categoryTitle = findViewById(R.id.activity_workshops_txt_category_title);


        mCategoryTitleTv = findViewById(R.id.activity_workshops_txt_category_title);
        mCategoryTitleTv.setText(categorySelected);


        // Radiobutton

        // RecyclerView for whole activity
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_workshop_workshops);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWorkshopAdapter = new WorkshopAdapter((ArrayList<Product>) mWorkshops, this, getBaseContext());
        mRecyclerView.setAdapter(mWorkshopAdapter);

        SearchView searchView = (SearchView) findViewById(R.id.activity_workshops_search);
        searchView.setQueryHint("Zoeken");

        final boolean[] automaticChangedCategory = {false};
        final boolean[] automaticChangedSearch = {false};

        CategoryAdapter ca = new CategoryAdapter(root ,this, WorkshopActivity.this, new CategoryAdapter.Listener() {
            @Override
            public void onChange(String filterLabel) {

                if(!automaticChangedCategory[0]){
                    searchValue = "";
                    categoryTitle.setText(filterLabel);

                    automaticChangedSearch[0] = true;
                    searchView.setQuery("", false);
                    automaticChangedSearch[0] = false;

                    categorySelected = filterLabel;
                    filter();
                }

            }
        });

        // SearchView

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!automaticChangedSearch[0]){
                    categoryTitle.setText("Zoekresultaten");
                    automaticChangedCategory[0] = true;
                    ca.setChecked();
                    automaticChangedCategory[0] = false;
                    searchValue = s;
                    if(s.isEmpty()){
                        categorySelected = "Alles";
                    } else {
                        categorySelected = "";
                    }
                    filter();
                }
                return false;
            }
        });

        mWorkshopAdapter.notifyDataSetChanged();
    }


    @Override
    public void onWorkshopSelected(int position) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
     }

    public List<Product> filter(){
        List<Product> filteredList = new ArrayList<>();

        if(!searchValue.isEmpty()){
            for (Product workshop: mWorkshops) {
                if(workshop.getName().toLowerCase().contains(searchValue)){
                    filteredList.add(workshop);
                }
            }
        } else if(!categorySelected.isEmpty()){
            if(categorySelected.equals("Alles")){
                filteredList.addAll(mWorkshops);
            } else if(categorySelected.equals("Meest gekozen")){
                for (Product workshop: mWorkshops) {
                    if(workshop.isHighlighted()){
                        filteredList.add(workshop);
                    }
                }
            } else {
                for (Product workshop: mWorkshops) {
                    if(workshop.getCategory().equals(categorySelected)){
                        filteredList.add(workshop);
                    }
                }
            }
        }

        mWorkshopAdapter.setWorkshopList(filteredList);
        return filteredList;
    }

}