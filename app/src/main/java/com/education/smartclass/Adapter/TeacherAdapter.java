package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.Holder;
import com.education.smartclass.holder.TeacherHolder;
import com.education.smartclass.models.Organisation;
import com.education.smartclass.models.Teachers;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherHolder> {

    Context c;
    ArrayList<Teachers> teacherList;

    public TeacherAdapter(Context c, ArrayList<Teachers> teacherList) {
        this.c = c;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_list_row, null);
        return new TeacherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherHolder holder, int position) {
        holder.teacherName.setText(teacherList.get(position).getTeacherName());
        holder.teacherCode.setText(teacherList.get(position).getTeacherCode());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
