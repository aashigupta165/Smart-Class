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
import com.education.smartclass.holder.StudentAssignmentListHolder;
import com.education.smartclass.models.AssignmentDetailsList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentAssignmentListAdapter extends RecyclerView.Adapter<StudentAssignmentListHolder> implements Filterable {

    private Context c;
    private ArrayList<AssignmentDetailsList> studentAssignmentDetailsLists;

    private ArrayList<AssignmentDetailsList> filterList;

    private StudentAssignmentListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(StudentAssignmentListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public StudentAssignmentListAdapter(Context c, ArrayList<AssignmentDetailsList> studentAssignmentDetailsLists) {
        this.c = c;
        this.studentAssignmentDetailsLists = studentAssignmentDetailsLists;
        filterList = new ArrayList<>(studentAssignmentDetailsLists);
    }

    @NonNull
    @Override
    public StudentAssignmentListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_assignment_list_row, null);
        return new StudentAssignmentListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAssignmentListHolder studentAssignmentListHolder, int position) {
        studentAssignmentListHolder.subject.setText(studentAssignmentDetailsLists.get(position).getSubjectAssignment());
        studentAssignmentListHolder.time.setText("Uploaded at: " + studentAssignmentDetailsLists.get(position).getAssignmentTime() + "(" +
                studentAssignmentDetailsLists.get(position).getAssignmentTime() + ")");
        if (studentAssignmentDetailsLists.get(position).getActive().equals("true")) {
            studentAssignmentListHolder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_submit));
        } else {
            studentAssignmentListHolder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_missing));
        }
        studentAssignmentListHolder.title.setText(studentAssignmentDetailsLists.get(position).getAssignmentTitle());
        if (studentAssignmentDetailsLists.get(position).getTeacherRemark().equals("")) {
            studentAssignmentListHolder.remark_card.setVisibility(View.GONE);
        } else {
            studentAssignmentListHolder.remark.setText(studentAssignmentDetailsLists.get(position).getTeacherRemark());
        }
    }

    @Override
    public int getItemCount() {
        return studentAssignmentDetailsLists.size();
    }

    @Override
    public Filter getFilter() {
        return filterOne;
    }

    private Filter filterOne = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<AssignmentDetailsList> filteredList = new ArrayList<>();

            if (constraint.equals("all")) {
                filteredList = filterList;
            } else {
                try {
                    SimpleDateFormat datequery = new SimpleDateFormat("dd-MM-yyyy");
                    Date now = datequery.parse(constraint.toString());
                    for (AssignmentDetailsList studentAssignmentDetailsList : filterList) {
                        Date date = datequery.parse(studentAssignmentDetailsList.getAssignmentDate());
                        if (now.compareTo(date) == 0) {
                            filteredList.add(studentAssignmentDetailsList);
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
            studentAssignmentDetailsLists.clear();
            studentAssignmentDetailsLists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
