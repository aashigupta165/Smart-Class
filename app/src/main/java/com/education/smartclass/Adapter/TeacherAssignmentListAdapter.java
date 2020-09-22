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
import com.education.smartclass.holder.TeacherAssignmentListHolder;
import com.education.smartclass.models.TeacherAssignmentDetailsList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherAssignmentListAdapter extends RecyclerView.Adapter<TeacherAssignmentListHolder> implements Filterable {

    private Context c;
    private ArrayList<TeacherAssignmentDetailsList> teacherAssignmentDetailsLists;

    private ArrayList<TeacherAssignmentDetailsList> filterList;

    private TeacherAssignmentListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(TeacherAssignmentListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public TeacherAssignmentListAdapter(Context c, ArrayList<TeacherAssignmentDetailsList> teacherAssignmentDetailsLists) {
        this.c = c;
        this.teacherAssignmentDetailsLists = teacherAssignmentDetailsLists;
        filterList = new ArrayList<>(teacherAssignmentDetailsLists);
    }

    @NonNull
    @Override
    public TeacherAssignmentListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_assignment_list_row, null);
        return new TeacherAssignmentListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherAssignmentListHolder teacherAssignmentListHolder, int position) {
        teacherAssignmentListHolder.time.setText("Uploaded at: " + teacherAssignmentDetailsLists.get(position).getAssignmentTime() + "(" +
                teacherAssignmentDetailsLists.get(position).getAssignmentDate() + ")");
        teacherAssignmentListHolder.title.setText(teacherAssignmentDetailsLists.get(position).getAssignmentTitle());
        teacherAssignmentListHolder.class_subject.setText(teacherAssignmentDetailsLists.get(position).getClassAssignment() + "-" +
                teacherAssignmentDetailsLists.get(position).getSectionAssignment() + "(" + teacherAssignmentDetailsLists.get(position).getSubjectAssignment() + ")");
        if (teacherAssignmentDetailsLists.get(position).getDescription().equals("")) {
            teacherAssignmentListHolder.desc_card.setVisibility(View.GONE);
        } else {
            teacherAssignmentListHolder.description.setText(teacherAssignmentDetailsLists.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return teacherAssignmentDetailsLists.size();
    }

    @Override
    public Filter getFilter() {
        return filterOne;
    }

    private Filter filterOne = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<TeacherAssignmentDetailsList> filteredList = new ArrayList<>();

            if (constraint.equals("all")) {
                filteredList = filterList;
            } else {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy");
                    Date now = datequery.parse(constraint.toString());
                    for (TeacherAssignmentDetailsList teacherAssignmentDetailsList : filterList) {
                        Date date = datequery.parse(teacherAssignmentDetailsList.getAssignmentDate());
                        if (now.compareTo(date) == 0) {
                            filteredList.add(teacherAssignmentDetailsList);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            teacherAssignmentDetailsLists.clear();
            teacherAssignmentDetailsLists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
