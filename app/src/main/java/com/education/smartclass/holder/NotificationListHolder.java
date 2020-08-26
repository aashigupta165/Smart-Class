package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class NotificationListHolder extends RecyclerView.ViewHolder {

    public TextView notification;

    public NotificationListHolder(@NonNull View itemView) {
        super(itemView);

        this.notification = itemView.findViewById(R.id.notification_row);
    }
}
