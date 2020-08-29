package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.ClassListHolder;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.models.OrgSubjects;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListHolder> {

    Context c;
    ArrayList<OrgClassList> classLists;

    public ClassListAdapter(Context c, ArrayList<OrgClassList> classLists) {
        this.c = c;
        this.classLists = classLists;
    }

    @NonNull
    @Override
    public ClassListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_list_row, null);
        return new ClassListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassListHolder holder, int position) {
        holder.class_section.setText(classLists.get(position).getOrgClass() + "-" + classLists.get(position).getOrgSection());
        String orgSubjects = "";
        for (OrgSubjects sub : classLists.get(position).getOrgSubjects()) {
            orgSubjects += sub.getSubject() + ", ";
        }
        orgSubjects = orgSubjects.substring(0, orgSubjects.length() - 4);
        holder.subject.setText(orgSubjects);
    }

    @Override
    public int getItemCount() {
        return classLists.size();
    }
}
