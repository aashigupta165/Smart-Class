package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherClassListHolder;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.models.TeacherSubjects;

import java.util.ArrayList;

public class TeacherClassListAdapter extends RecyclerView.Adapter<TeacherClassListHolder> {

    Context c;
    ArrayList<TeacherClasses> studentList;

    public TeacherClassListAdapter(Context c, ArrayList<TeacherClasses> studentList) {
        this.c = c;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public TeacherClassListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_class_list, null);
        return new TeacherClassListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassListHolder teacherClassListHolder, int position) {
        teacherClassListHolder.className.setText(studentList.get(position).getTeacherClass() + " " + studentList.get(position).getTeacherSection());
        String teacherSubjects = "";
        for (TeacherSubjects sub : studentList.get(position).getTeachingSubjects()) {
            teacherSubjects += sub.getSubject() + ", ";
        }
        teacherSubjects = teacherSubjects.substring(0, teacherSubjects.length() - 2);
        teacherClassListHolder.subject.setText(teacherSubjects);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
