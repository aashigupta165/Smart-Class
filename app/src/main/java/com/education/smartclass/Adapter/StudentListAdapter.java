package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.StudentListHolder;
import com.education.smartclass.models.StudentDetail;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListHolder> {

    Context c;
    ArrayList<StudentDetail> studentDetails;

    public StudentListAdapter(Context c, ArrayList<StudentDetail> studentDetails) {
        this.c = c;
        this.studentDetails = studentDetails;
    }

    @NonNull
    @Override
    public StudentListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_row, null);
        return new StudentListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListHolder holder, int position) {
        holder.studentName.setText(studentDetails.get(position).getStudentName());
        holder.studentRollNo.setText(studentDetails.get(position).getStudentRollNo());
        holder.class_section.setText(studentDetails.get(position).getStudentClass() + "-" + studentDetails.get(position).getStudentSection());
    }

    @Override
    public int getItemCount() {
        return studentDetails.size();
    }
}
