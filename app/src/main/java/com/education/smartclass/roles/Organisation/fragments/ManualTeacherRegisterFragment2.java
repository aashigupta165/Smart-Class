package com.education.smartclass.roles.Organisation.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

public class ManualTeacherRegisterFragment2 extends Fragment {

    private EditText teacherName, teacherAge, teacherDesignation, teacherCode;
    private RadioGroup teacherGender;
    private RadioButton radioButton;
    private String gender = "male";
    private TextView nextbtn;

    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manual_teacher_register2, container, false);

        teacherName = view.findViewById(R.id.teacher_Name);
        teacherAge = view.findViewById(R.id.teacher_Age);
        teacherDesignation = view.findViewById(R.id.teacher_Designation);
        teacherCode = view.findViewById(R.id.teacher_Code);
        teacherGender = view.findViewById(R.id.teacher_gender);
        nextbtn = view.findViewById(R.id.nextbtn);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        teacherGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);
                if (radioButton.getText().toString().equals("Male")) {
                    gender = "male";
                } else {
                    gender = "female";
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
        return view;
    }

    private void checkDetails() {

        String TeacherName = teacherName.getText().toString().trim();
        String TeacherAge = teacherAge.getText().toString().trim();
        String TeacherDesignation = teacherDesignation.getText().toString().trim();
        String TeacherCode = teacherCode.getText().toString().trim();

        if (TeacherName.isEmpty() || TeacherAge.isEmpty() || TeacherDesignation.isEmpty() || TeacherCode.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details.");
            return;
        }

        if (Integer.parseInt(TeacherAge) < 18 || Integer.parseInt(TeacherAge) > 100) {
            new SnackBar(relativeLayout, "Please Enter Valid Details.");
            return;
        }

        Bundle bundle = this.getArguments();
        bundle.putString("teacherName", TeacherName);
        bundle.putString("teacherAge", TeacherAge);
        bundle.putString("teacherDesignation", TeacherDesignation);
        bundle.putString("teacherCode", TeacherCode);
        bundle.putString("teacherGender", gender);

        ManualTeacherRegisterFragment3 fragment = new ManualTeacherRegisterFragment3();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}