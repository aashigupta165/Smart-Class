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
    ArrayList<StudentDetail> studentList;

    public StudentListAdapter(Context c, ArrayList<StudentDetail> studentList) {
        this.c = c;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list, null);
        return new StudentListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListHolder studentListHolder, int position) {
        studentListHolder.studentName.setText(studentList.get(position).getStudentName());
        studentListHolder.studentRollNo.setText(studentList.get(position).getStudentRollNo());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
