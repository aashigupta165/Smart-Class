package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class Holder extends RecyclerView.ViewHolder {

    public TextView orgName;
    public TextView orgCode;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.orgName = itemView.findViewById(R.id.image);
        this.orgCode = itemView.findViewById(R.id.title);
    }
}
