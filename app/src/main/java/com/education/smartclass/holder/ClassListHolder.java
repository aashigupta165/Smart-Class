package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class ClassListHolder extends RecyclerView.ViewHolder {

    public TextView class_section;
    public TextView subject;

    public ClassListHolder(@NonNull View itemView) {
        super(itemView);

        this.class_section = itemView.findViewById(R.id.class_section);
        this.subject = itemView.findViewById(R.id.subject);
    }
}
