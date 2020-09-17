package com.education.smartclass.roles.teacher.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.R;

public class TeacherAssignmentFragment extends Fragment {

    private TextView heading, no_data;
    private ImageView filter;
    private ProgressBar progressBar;
    private RecyclerView assignment_list;

    private RelativeLayout relativeLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_questionaire, container, false);

        heading = view.findViewById(R.id.heading);
        heading.setText("Assignment");
        filter = view.findViewById(R.id.filter);
        no_data = view.findViewById(R.id.no_question);
        no_data.setText("No Assignment Available");
        assignment_list = view.findViewById(R.id.question_list);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

//        dataObserver();
//        buttonClickEvents();

        return view;
    }
}
