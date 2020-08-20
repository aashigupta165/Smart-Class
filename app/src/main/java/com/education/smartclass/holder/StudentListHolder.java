package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class StudentListHolder extends RecyclerView.ViewHolder {

    public TextView studentRollNo;
    public TextView studentName;

    public StudentListHolder(@NonNull View itemView) {
        super(itemView);

        this.studentRollNo = itemView.findViewById(R.id.roll_no);
        this.studentName = itemView.findViewById(R.id.name);
    }
}
