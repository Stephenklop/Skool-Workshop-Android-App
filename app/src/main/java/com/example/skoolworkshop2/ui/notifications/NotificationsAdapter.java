package com.example.skoolworkshop2.ui.notifications;

import android.app.Application;
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

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewholder> {
    private List<Notification> notifications;
    private Application application;
    private ClickListener listener;

    public NotificationsAdapter(List<Notification> notifications, Application application, ClickListener listener){
        System.out.println(notifications);
        this.notifications = notifications;
        this.application = application;
        this.listener = listener;
    }

    public class NotificationViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mNotificationTitle;
        public TextView mNotificationDescription;
        public TextView mNotificationLink;

        public NotificationViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            mNotificationTitle = itemView.findViewById(R.id.item_notification_tv_header);
            mNotificationDescription = itemView.findViewById(R.id.item_notification_tv_msg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LocalDb.getDatabase(application).getNotificationDAO().setNotificationRead(notifications.get(getAdapterPosition()).getId());
            listener.onClick();
        }
    }

    @NonNull
    @NotNull
    @Override
    public NotificationViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_notification;
        boolean shouldAttachToParent = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, shouldAttachToParent);
        return new NotificationViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationViewholder holder, int position) {

        Notification notification = notifications.get(position);
        holder.mNotificationTitle.setText(notification.getTitle());
        if (!notification.getUrl().equals("undefined")) {
            holder.mNotificationDescription .setText(notification.getDescription() + "\n" + notification.getUrl());
        } else {
            holder.mNotificationDescription .setText(notification.getDescription());
        }
    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public interface ClickListener {
        void onClick();
    }
}
