package com.example.skoolworkshop2.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.ui.workshop.WorkshopAdapter;

import java.util.ArrayList;

public class CategoryAdapter{

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final ArrayList<String> categories = new ArrayList<>();

    private RadioGroup mCategoriesRadiogroup;
    private AppCompatActivity activity;
    private ArrayList<Workshop> mWorkshops;
    private WorkshopAdapter mWorkshopAdapter;
    private Listener listener;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CategoryAdapter(View root, Context context, AppCompatActivity activity, Listener listener) {
        Log.d(LOG_TAG, "Constructor aangeroepen");

        this.activity = activity;
        this.context = context;
        mCategoriesRadiogroup = root.findViewById(R.id.activity_workshop_rg_categories);
        this.listener = listener;
        addCategoriesToGroup();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addCategoriesToGroup() {
        String[] categorieArray = context.getResources().getStringArray(R.array.category);

        for (int i = 0; i < categorieArray.length; i++) {
            int paddingDp = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, activity.getResources().getDisplayMetrics()));

            RadioButton rb = (RadioButton) LayoutInflater.from(context).inflate(R.layout.component_button_categories, mCategoriesRadiogroup, false);

            rb.setText(categorieArray[i]);
            rb.setTag(categorieArray[i]);
            rb.setId(i);

            mCategoriesRadiogroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = activity.findViewById(checkedId);
                listener.onChange(radioButton.getTag().toString());
            });

            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        rb.setTextColor(Color.WHITE);
                    } else {
                        rb.setTextColor(Color.BLACK);
                    }
                }
            });

            mCategoriesRadiogroup.addView(rb);
            if(i == 0){
                rb.setChecked(true);
            }
        }
    }



    public interface Listener {
        void onChange(String filterLabel);
    }
}

