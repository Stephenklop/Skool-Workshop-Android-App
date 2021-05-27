package com.example.skoolworkshop2;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.domain.Workshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryGridViewHolder> {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final ArrayList<String> categories;
    private OnCategorySelectionListener listener;

    public CategoryAdapter(ArrayList<String> categoriesArrayList, OnCategorySelectionListener listener) {
        Log.d(LOG_TAG, "Constructor aangeroepen");
        this.categories = categoriesArrayList;
        this.categories.addAll(addCategories());
        this.listener = listener;
    }

    // Hard-coded categories
    public ArrayList<String> addCategories(){
        ArrayList<String> list = new ArrayList<>();
        categories.add("Meest gekozen");
        categories.add("Beeldende Kunst");
        categories.add("Dans");
        categories.add("Media");
        categories.add("Muziek");
        categories.add("Sport");
        categories.add("Theater");

        return list;
    }

    public class CategoryGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Button mCategoryButton;

        public CategoryGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryButton = (Button) itemView.findViewById(R.id.medium_button_extendable);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(LOG_TAG, "onClick on item " + getAdapterPosition());
            listener.onCategorySelected(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CategoryGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdListedItem = R.layout.fragment_button_medium_extendable;
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutIdListedItem, parent, shouldAttachToParentImmediately);
        return new CategoryGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryGridViewHolder holder, int position) {
        String category = categories.get(position);
        if(position != 0){
            holder.mCategoryButton.setText(category);
            Log.d(LOG_TAG, "onBindViewHolder - " + category);
            holder.mCategoryButton.setTextColor(Color.BLACK);
        } else {
            holder.mCategoryButton.setText(category);
            Log.d(LOG_TAG, "onBindViewHolder - " + category);
        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnCategorySelectionListener {
        void onCategorySelected(int position);
    }
}

