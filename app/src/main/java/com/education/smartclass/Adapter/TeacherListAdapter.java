package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherListHolder;
import com.education.smartclass.models.Teachers;

import java.util.ArrayList;
import java.util.List;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListHolder> implements Filterable {

    Context c;
    ArrayList<Teachers> teacherList;
    ArrayList<Teachers> teachersListFull;

    private TeacherListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(TeacherListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public TeacherListAdapter(Context c, ArrayList<Teachers> teacherList) {
        this.c = c;
        this.teacherList = teacherList;
        teachersListFull = new ArrayList<>(teacherList);
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

    @Override
    public Filter getFilter() {
        return teacherFilter;
    }

    public Filter teacherFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Teachers> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(teachersListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Teachers item : teachersListFull) {
                    if (item.getTeacherName().toLowerCase().contains(filterPattern) || item.getTeacherCode().toLowerCase().contains(filterPattern)) {
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
            teacherList.clear();
            teacherList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
