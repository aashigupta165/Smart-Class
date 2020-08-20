package com.education.smartclass.roles.teacher.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.storage.SharedPrefManager;

public class TeacherProfileFragment extends Fragment {

    private TextView name, designation, id, email, mobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        name = view.findViewById(R.id.teacher_name);
        designation = view.findViewById(R.id.teacher_designation);
        id = view.findViewById(R.id.teacher_code);
        email = view.findViewById(R.id.teacher_email);
        mobile = view.findViewById(R.id.teacher_mobile);

        name.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherName());
        designation.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherDesignation());
        id.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
        email.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherEmail());
        mobile.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherMobile());

        return  view;
    }
}