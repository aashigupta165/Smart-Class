package com.education.smartclass.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class StudentListHolder extends RecyclerView.ViewHolder {

    public TextView studentName;
    public TextView studentRollNo;
    public TextView class_section;

    public StudentListHolder(@NonNull View itemView) {
        super(itemView);

        this.studentName = itemView.findViewById(R.id.student_name);
        this.studentRollNo = itemView.findViewById(R.id.student_rollno);
        this.class_section = itemView.findViewById(R.id.class_section);
    }
}
