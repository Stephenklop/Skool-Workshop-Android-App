package com.example.skoolworkshop2;

import android.app.Activity;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.domain.Workshop;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopGridViewHolder> {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final ArrayList<Workshop> workshopArrayList;
    private OnWorkshopSelectionListener listener;


    public WorkshopAdapter(ArrayList<Workshop> workshopArrayList, OnWorkshopSelectionListener listener) {
        Log.d(LOG_TAG, "Constructor aangeroepen");
        this.workshopArrayList = new ArrayList<>(workshopArrayList);
        Log.d(LOG_TAG, "WorkshopAdapter: Size" + workshopArrayList.size());
        this.listener = listener;
    }

    public void setWorkshopList(List<Workshop> workshops) {
        Log.d(LOG_TAG, "setMovieList");
        this.workshopArrayList.clear();
        this.workshopArrayList.addAll(workshops);
        this.notifyDataSetChanged();
    }

    public class WorkshopGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mWorkshopName;
        public TextView mWorkshopCategory;
        public ImageView mWorkshopImage;

        public WorkshopGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mWorkshopName = (TextView) itemView.findViewById(R.id.tv_workshop_title);
            mWorkshopImage = (ImageView) itemView.findViewById(R.id.img_workshop_card);
            mWorkshopImage.setClipToOutline(true);
            mWorkshopCategory = (TextView) itemView.findViewById(R.id.tv_workshop_category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(LOG_TAG, "onClick on item " + getAdapterPosition());
            listener.onWorkshopSelected(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), WorkshopDetailActivity.class);
            intent.putExtra("Workshop", workshopArrayList.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
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
        holder.mWorkshopCategory.setText(workshop.getCategory().label);
    }

    @Override
    public int getItemCount() {
        return workshopArrayList.size();
    }

    public interface OnWorkshopSelectionListener {
        void onWorkshopSelected(int position);
    }
}
