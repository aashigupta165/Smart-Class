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
import com.education.smartclass.holder.StudentListHolder;
import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.models.Teachers;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListHolder> implements Filterable {

    Context c;
    ArrayList<StudentDetail> studentDetails;
    ArrayList<StudentDetail> studentDetailsFull;

    public StudentListAdapter(Context c, ArrayList<StudentDetail> studentDetails) {
        this.c = c;
        this.studentDetails = studentDetails;
        studentDetailsFull = new ArrayList<>(studentDetails);
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

    @Override
    public Filter getFilter() {
        return studentFilter;
    }

    public Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudentDetail> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(studentDetailsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StudentDetail item : studentDetailsFull) {
                    if (item.getStudentName().toLowerCase().contains(filterPattern) || item.getStudentRollNo().toLowerCase().contains(filterPattern) ||
                            (item.getStudentClass().toLowerCase() + "-" + item.getStudentSection().toLowerCase()).contains(filterPattern)) {
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
            studentDetails.clear();
            studentDetails.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
