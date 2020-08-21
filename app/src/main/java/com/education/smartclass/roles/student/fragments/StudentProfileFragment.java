package com.education.smartclass.roles.student.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.storage.SharedPrefManager;

public class StudentProfileFragment extends Fragment {

    private TextView name, rollno, class_section, dob, email, mobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        name = view.findViewById(R.id.student_name);
        rollno = view.findViewById(R.id.student_rollno);
        class_section = view.findViewById(R.id.student_class);
        dob = view.findViewById(R.id.student_dob);
        email = view.findViewById(R.id.student_email);
        mobile = view.findViewById(R.id.student_mobile);

        name.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentName());
        rollno.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo());
        class_section.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentClass() + " " +
                SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
        dob.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentDOB());
        email.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentEmail());
        mobile.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentMobile());

        return view;
    }
}