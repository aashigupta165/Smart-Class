package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.NotificationListHolder;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationListHolder> {

    Context c;
    ArrayList<String> notificationList;

    public NotificationsListAdapter(Context c, ArrayList<String> notificationList) {
        this.c = c;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_row, null);
        return new NotificationListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListHolder notificationListHolder, int position) {
        notificationListHolder.notification.setText(notificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
