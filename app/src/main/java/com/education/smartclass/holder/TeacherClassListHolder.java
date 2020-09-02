package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class TeacherClassListHolder extends RecyclerView.ViewHolder {

    public TextView className;
    public TextView subject;

    public TeacherClassListHolder(@NonNull View itemView) {
        super(itemView);

        this.className = itemView.findViewById(R.id.className);
        this.subject = itemView.findViewById(R.id.subject);
    }
}
