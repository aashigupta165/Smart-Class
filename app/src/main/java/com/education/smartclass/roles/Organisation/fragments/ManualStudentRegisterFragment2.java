package com.education.smartclass.roles.Organisation.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.model.StudentRegisterManualViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

public class ManualStudentRegisterFragment2 extends Fragment {

    private EditText studentName, studentRollNo, teacherDesignation, studentClass, studentSection, studentFather;
    private RadioGroup studentGender;
    private RadioButton radioButton;
    private String gender = "male";
    private TextView submitbtn, heading;

    private RelativeLayout relativeLayout;
    private LinearLayout class_section;

    private ProgressDialog progressDialog;

    private StudentRegisterManualViewModel studentRegisterManualViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manual_teacher_register2, container, false);

        class_section = view.findViewById(R.id.student_Class_Section);
        class_section.setVisibility(View.VISIBLE);
        studentName = view.findViewById(R.id.subject);
        studentRollNo = view.findViewById(R.id.className);
        teacherDesignation = view.findViewById(R.id.date);
        teacherDesignation.setVisibility(View.INVISIBLE);
        studentClass = view.findViewById(R.id.student_Class);
        studentSection = view.findViewById(R.id.student_Section);
        studentFather = view.findViewById(R.id.Time);
        studentGender = view.findViewById(R.id.teacher_gender);
        submitbtn = view.findViewById(R.id.nextbtn);
        heading = view.findViewById(R.id.heading);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        heading.setText("Create Student");
        studentName.setHint("Student Name");
        studentRollNo.setHint("Student Roll No.");
        studentFather.setHint("Father's Name");
        teacherDesignation.setHint("Class-section");
        submitbtn.setText("Submit");

        studentGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        progressDialog = new ProgressDialog(getContext());

        buttonClickEvents();
        dataObserver();

        return view;
    }

    private void buttonClickEvents() {

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(submitbtn.getWindowToken(), 0);
                registerStudent();
            }
        });
    }

    private void dataObserver() {
        studentRegisterManualViewModel = ViewModelProviders.of(this).get(StudentRegisterManualViewModel.class);
        LiveData<String> message = studentRegisterManualViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "student_created":
                        new SnackBar(relativeLayout, "Student Registered");
                        StudentFragment fragment = new StudentFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        break;
                    case "invalid_entry":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });
    }

    private void registerStudent() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String StudentName = studentName.getText().toString().trim();
        String StudentRollNo = studentRollNo.getText().toString().trim();
        String StudentClass = studentClass.getText().toString().trim();
        String StudentSection = studentSection.getText().toString().trim();
        String StudentFather = studentFather.getText().toString().trim();

        if (StudentName.isEmpty() || StudentRollNo.isEmpty() || StudentClass.isEmpty() || StudentFather.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details.");
            return;
        }

        if (studentSection.equals("")) {
            StudentSection = "A";
        }

        Bundle bundle = this.getArguments();

        String email = bundle.getString("email");
        String mobile = bundle.getString("mobile");
        String dob = bundle.getString("dob");
        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        studentRegisterManualViewModel.register(StudentName, StudentRollNo, StudentClass, StudentSection, StudentFather, email, mobile, dob,
                gender, orgCode);
    }
}
