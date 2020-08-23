package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherListHolder;
import com.education.smartclass.models.Teachers;

import java.util.ArrayList;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListHolder> {

    Context c;
    ArrayList<Teachers> teacherList;

    private TeacherListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(TeacherListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public TeacherListAdapter(Context c, ArrayList<Teachers> teacherList) {
        this.c = c;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_list_row, null);
        return new TeacherListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListHolder holder, int position) {
        holder.teacherName.setText(teacherList.get(position).getTeacherName());
        holder.teacherCode.setText(teacherList.get(position).getTeacherCode());

        if (teacherList.get(position).getActive().equals("false")) {
            holder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_check_box_outline));
        } else {
            holder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_check_box));
        }
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
