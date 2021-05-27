package com.example.skoolworkshop2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopGridViewHolder> implements Filterable {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final ArrayList<Workshop> workshopArrayList;
    private final ArrayList<String> categories = new ArrayList<>();
    private OnWorkshopSelectionListener listener;

    public WorkshopAdapter(ArrayList<Workshop> workshopArrayList, OnWorkshopSelectionListener listener) {
        Log.d(LOG_TAG, "Constructor aangeroepen");
        addCategories();
        this.workshopArrayList = workshopArrayList;
        workshopArrayList.add(new Workshop(1, "Test", "Test", 55.55, 6, "Sport"));
        workshopArrayList.add(new Workshop(1, "Test", "Test", 55.55, 6, ""));
        workshopArrayList.add(new Workshop(1, "Result", "Test", 55.55, 6, "Test"));
        Log.d(LOG_TAG, "WorkshopAdapter: Size" + workshopArrayList.size());
        this.listener = listener;
    }

    // Hard-coded categories
    public void addCategories(){
        ArrayList<String> list = new ArrayList<>();
        categories.add("Meest gekozen");
        categories.add("Beeldende Kunst");
        categories.add("Dans");
        categories.add("Media");
        categories.add("Muziek");
        categories.add("Sport");
        categories.add("Theater");
    }


    // filtereren
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Workshop> filteredList = new ArrayList<>();
            String filterPattern = constraint.toString().toLowerCase().trim();
            for (Workshop workshop : workshopArrayList) {
                if (workshop.getName().toLowerCase().contains(filterPattern)) {
                    filteredList.add(workshop);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            Log.d(LOG_TAG, "performFiltering: " + results);
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            workshopArrayList.clear();
            workshopArrayList.addAll((List) results.values);
            notifyDataSetChanged();
            Log.i(LOG_TAG, "publishResults: Characters: " + workshopArrayList);
        }
    };

    public class WorkshopGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mWorkshopName;
        public TextView mWorkshopCategory;
        public ImageView mWorkshopImage;

        public WorkshopGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mWorkshopName = (TextView) itemView.findViewById(R.id.tv_workshop_title);
            mWorkshopImage = (ImageView) itemView.findViewById(R.id.img_workshop_card);
            mWorkshopCategory = (TextView) itemView.findViewById(R.id.tv_workshop_category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(LOG_TAG, "onClick on item " + getAdapterPosition());
            listener.onWorkshopSelected(getAdapterPosition());
            Log.d(LOG_TAG, "onClick category selected" + getAdapterPosition());
            listener.onWorkshopCategorySelected(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public WorkshopGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIdListedItem = R.layout.item_workshop;
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutIdListedItem, parent, shouldAttachToParentImmediately);
        return new WorkshopGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkshopGridViewHolder holder, int position) {
        Workshop workshop = workshopArrayList.get(position);
        Log.d(LOG_TAG, "onBindViewHolder - " + workshop.toString());

        holder.mWorkshopName.setText(workshop.getName());
        holder.mWorkshopCategory.setText(workshop.getCategory());
    }


    @Override
    public int getItemCount() {
        return workshopArrayList.size();
    }

    public interface OnWorkshopSelectionListener {
        void onWorkshopSelected(int position);
        void onWorkshopCategorySelected(int position);
    }
}
