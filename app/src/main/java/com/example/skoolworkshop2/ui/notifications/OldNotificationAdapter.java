package com.example.skoolworkshop2.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OldNotificationAdapter extends RecyclerView.Adapter<OldNotificationAdapter.OldNotificationViewholder> {
    private List<Notification> notifications;
    private Context context;

    public OldNotificationAdapter(List<Notification> notifications, Context context){
        System.out.println(notifications);
        this.notifications = notifications;
        this.context = context;
    }

    public class OldNotificationViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mNotificationTitle;
        public TextView mNotificationDescription;
        public TextView mNotificationLink;

        public OldNotificationViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            mNotificationTitle = itemView.findViewById(R.id.item_old_notification_tv_header);
            mNotificationDescription = itemView.findViewById(R.id.item_old_notification_tv_msg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @NotNull
    @Override
    public OldNotificationViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        int layoutOldId = R.layout.item_old_notification;
        boolean shouldAttachToParent = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutOldId, parent, shouldAttachToParent);
        return new OldNotificationViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OldNotificationViewholder holder, int position) {

        Notification notification = notifications.get(position);
        holder.mNotificationTitle.setText(notification.getTitle());
        holder.mNotificationDescription .setText(notification.getDescription());
    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
