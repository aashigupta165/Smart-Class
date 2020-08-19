package com.education.smartclass.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class TeacherHolder extends RecyclerView.ViewHolder {

    public TextView teacherName;
    public TextView teacherCode;
    public ImageView status;

    public TeacherHolder(@NonNull View itemView) {
        super(itemView);

        this.teacherName = itemView.findViewById(R.id.teacher_Name);
        this.teacherCode = itemView.findViewById(R.id.teacher_Code);
        this.status = itemView.findViewById(R.id.status);
    }
}
