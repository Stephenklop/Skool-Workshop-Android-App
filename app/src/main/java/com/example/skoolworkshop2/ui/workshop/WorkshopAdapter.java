package com.example.skoolworkshop2.ui.workshop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.WorkshopItem;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopGridViewHolder>{

    private final String LOG_TAG = this.getClass().getSimpleName();
    private final List<Product> workshopArrayList;
    private OnWorkshopSelectionListener listener;
    private Context context;


    public WorkshopAdapter(List<Product> workshopArrayList, OnWorkshopSelectionListener listener, Context context) {
        Log.d(LOG_TAG, "Constructor aangeroepen");
        this.workshopArrayList = new ArrayList<>(workshopArrayList);
        Log.d(LOG_TAG, "WorkshopAdapter: Size" + workshopArrayList.size());
        this.listener = listener;
        this.context = context;
    }

    public void setWorkshopList(List<Product> workshops) {
        Log.d(LOG_TAG, "setMovieList");
        this.workshopArrayList.clear();
        this.workshopArrayList.addAll(workshops);
        this.notifyDataSetChanged();
    }

    public class WorkshopGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mWorkshopImage;
        public TextView mWorkshopName;
        public TextView mWorkshopCategory;

        public WorkshopGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mWorkshopImage = (ImageView) itemView.findViewById(R.id.img_workshop_card);
            mWorkshopImage.setClipToOutline(true);
            mWorkshopName = (TextView) itemView.findViewById(R.id.tv_workshop_title);
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

        Product workshop = workshopArrayList.get(position);
        Log.d(LOG_TAG, "onBindViewHolder - " + workshop.toString());

        Glide.with(context).load(workshop.getSourceImage()).centerCrop().into(holder.mWorkshopImage);
        holder.mWorkshopName.setText(workshop.getName());
        // TODO: Fix category
        holder.mWorkshopCategory.setText("");
    }

    @Override
    public int getItemCount() {
        return workshopArrayList.size();
    }

    public interface OnWorkshopSelectionListener {
        void onWorkshopSelected(int position);
    }
}
