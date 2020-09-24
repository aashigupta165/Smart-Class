package com.education.smartclass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherFetchStudentAssignmentListHolder;
import com.education.smartclass.models.TeacherFetchStudentAssignmentListDetails;

import java.util.ArrayList;

public class TeacherFetchStudentAssignmentListAdapter extends RecyclerView.Adapter<TeacherFetchStudentAssignmentListHolder> {

    Context c;
    ArrayList<TeacherFetchStudentAssignmentListDetails> teacherFetchStudentAssignmentListDetails;

    private TeacherFetchStudentAssignmentListHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(TeacherFetchStudentAssignmentListHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public TeacherFetchStudentAssignmentListAdapter(Context c, ArrayList<TeacherFetchStudentAssignmentListDetails> teacherFetchStudentAssignmentListDetails) {
        this.c = c;
        this.teacherFetchStudentAssignmentListDetails = teacherFetchStudentAssignmentListDetails;
    }

    @NonNull
    @Override
    public TeacherFetchStudentAssignmentListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_assignment_submission_list_row, null);
        return new TeacherFetchStudentAssignmentListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherFetchStudentAssignmentListHolder teacherFetchStudentAssignmentListHolder, int position) {
        teacherFetchStudentAssignmentListHolder.name_roll.setText(teacherFetchStudentAssignmentListDetails.get(position).getStudentName() + "(" +
                teacherFetchStudentAssignmentListDetails.get(position).getStudentRollNo() + ")");
        if (teacherFetchStudentAssignmentListDetails.get(position).getResponseActive().equals("true")) {
            teacherFetchStudentAssignmentListHolder.card.setCardBackgroundColor(ContextCompat.getColor(c, R.color.primaryLight));
            teacherFetchStudentAssignmentListHolder.download.setVisibility(View.VISIBLE);
            teacherFetchStudentAssignmentListHolder.add_remark.setVisibility(View.VISIBLE);
            teacherFetchStudentAssignmentListHolder.time.setText("Uploaded at: " + teacherFetchStudentAssignmentListDetails.get(position).getStudentTime() + "(" +
                    teacherFetchStudentAssignmentListDetails.get(position).getStudentDate());
            teacherFetchStudentAssignmentListHolder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_submit));
            if (teacherFetchStudentAssignmentListDetails.get(position).getStudentDescription().equals("")) {
                teacherFetchStudentAssignmentListHolder.description.setVisibility(View.GONE);
            } else {
                teacherFetchStudentAssignmentListHolder.description.setVisibility(View.VISIBLE);
                teacherFetchStudentAssignmentListHolder.description.setText(teacherFetchStudentAssignmentListDetails.get(position).getStudentDescription());
            }
            if (teacherFetchStudentAssignmentListDetails.get(position).getTeacherRemark().equals("")) {
                teacherFetchStudentAssignmentListHolder.add_remark.setVisibility(View.VISIBLE);
            } else {
                teacherFetchStudentAssignmentListHolder.add_remark.setVisibility(View.GONE);
                teacherFetchStudentAssignmentListHolder.remark.setText(teacherFetchStudentAssignmentListDetails.get(position).getTeacherRemark());
            }
        } else {
            teacherFetchStudentAssignmentListHolder.status.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ic_missing));
            teacherFetchStudentAssignmentListHolder.download.setVisibility(View.GONE);
            teacherFetchStudentAssignmentListHolder.add_remark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return teacherFetchStudentAssignmentListDetails.size();
    }
}
