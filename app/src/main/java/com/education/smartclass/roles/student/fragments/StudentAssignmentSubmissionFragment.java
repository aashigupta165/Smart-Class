package com.education.smartclass.roles.student.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.education.smartclass.R;

public class StudentAssignmentSubmissionFragment extends Fragment {

    private TextView subject, time, title, description, download;
    private ImageView status;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_assignment_submission, container, false);

        subject = view.findViewById(R.id.subject);
        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        download = view.findViewById(R.id.download_link);
        status = view.findViewById(R.id.status);

        Bundle bundle = this.getArguments();
        subject.setText(bundle.getString("subject"));
        title.setText(bundle.getString("title"));
        time.setText(bundle.getString("time") + "(" + bundle.getString("date") + ")");
        description.setText(bundle.getString("description"));
        if (bundle.getString("status").equals("true")) {
            status.setImageDrawable(getResources().getDrawable(R.drawable.ic_submit));
        } else {
            status.setImageDrawable(getResources().getDrawable(R.drawable.ic_missing));
        }

        return view;
    }
}