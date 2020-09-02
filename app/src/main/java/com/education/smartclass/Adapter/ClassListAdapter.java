package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.ClassListHolder;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.models.OrgSubjects;

import java.util.ArrayList;
import java.util.List;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListHolder> implements Filterable {

    Context c;
    ArrayList<OrgClassList> classLists;
    ArrayList<OrgClassList> classListsFull;

    public ClassListAdapter(Context c, ArrayList<OrgClassList> classLists) {
        this.c = c;
        this.classLists = classLists;
        classListsFull = new ArrayList<>(classLists);
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
            if (!sub.getSubject().equals("")) {
                orgSubjects += sub.getSubject() + ", ";
            }
        }
        orgSubjects = orgSubjects.substring(0, orgSubjects.length() - 2);
        holder.subject.setText(orgSubjects);
    }

    @Override
    public int getItemCount() {
        return classLists.size();
    }

    @Override
    public Filter getFilter() {
        return classFilter;
    }

    public Filter classFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OrgClassList> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(classListsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (OrgClassList item : classListsFull) {
                    if ((item.getOrgClass().toLowerCase() + "-" + item.getOrgSection().toLowerCase()).contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            classLists.clear();
            classLists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
