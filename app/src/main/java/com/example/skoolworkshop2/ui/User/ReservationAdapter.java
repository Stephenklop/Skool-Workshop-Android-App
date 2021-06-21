package com.example.skoolworkshop2.ui.User;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.domain.Order;
import com.example.skoolworkshop2.domain.Product;
import com.example.skoolworkshop2.domain.Reservation;
import com.example.skoolworkshop2.ui.WorkshopDetail.WorkshopDetailActivity;
import com.example.skoolworkshop2.ui.workshop.WorkshopAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationGridViewHolder> {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private List<Reservation> orderArrayList;
    private Context context;

    public ReservationAdapter(List<Reservation> orderArrayList, Context context) {
        Log.d(LOG_TAG, "Constructor aangeroepen");
        this.orderArrayList = new ArrayList<>(orderArrayList);
        this.context = context;
    }

    public class ReservationGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mInfo;
        public TextView mTitle;

        public ReservationGridViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.item_reservation_tv_header);
            mInfo = itemView.findViewById(R.id.item_reservation_tv_data);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @NonNull
    @NotNull
    @Override
    public ReservationGridViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        int layoutIdListedItem = R.layout.item_reservation;
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutIdListedItem, parent, shouldAttachToParentImmediately);
        return new ReservationAdapter.ReservationGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReservationAdapter.ReservationGridViewHolder holder, int position) {
        Reservation order = orderArrayList.get(position);
        Log.d(LOG_TAG, "onBindViewHolder - " + order.toString());

        holder.mTitle.setText(order.getId() + "");
        holder.mInfo.setText("Datum: " + order.getDate() + "\nStatus: " + order.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public void setList(List<Reservation> orderArrayList){
        this.orderArrayList = orderArrayList;
        notifyDataSetChanged();
    }
}
