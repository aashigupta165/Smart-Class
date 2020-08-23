package com.education.smartclass.roles.Organisation.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterManualViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class ManualTeacherRegisterFragment3 extends Fragment {

    private LinearLayout classDetailsList;
    private TextView addbtn, submitbtn;

    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;

    ArrayList<String> list = new ArrayList<>();

    private TeacherRegisterManualViewModel teacherRegisterManualViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
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
        View view = inflater.inflate(R.layout.activity_manual_teacher_register3, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        classDetailsList = view.findViewById(R.id.class_details_list);
        addbtn = view.findViewById(R.id.addbtn);
        submitbtn = view.findViewById(R.id.submitbtn);

        progressDialog = new ProgressDialog(getContext());

        buttonClickEvents();
        dataObserver();

        return view;
    }

    private void buttonClickEvents() {

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfValidAndRead()) {
                    registerTeacher();
                }
            }
        });
    }

    private boolean checkIfValidAndRead() {

        list.clear();
        boolean result = true;

        for (int i = 0; i < classDetailsList.getChildCount(); i++) {
            View view = classDetailsList.getChildAt(i);

            EditText className = view.findViewById(R.id.className);
            EditText section = view.findViewById(R.id.section);
            EditText subject = view.findViewById(R.id.subject);

            String temp = "";

            if (!className.getText().toString().equals("")) {
                temp += className.getText().toString() + "_";
            } else {
                result = false;
                break;
            }

            if (!section.getText().toString().equals("")) {
                temp += section.getText().toString() + "_";
            } else {
                result = false;
                break;
            }

            if (!subject.getText().toString().equals("")) {
                temp += subject.getText().toString();
            } else {
                result = false;
                break;
            }

            list.add(temp);
        }

        if (list.size() == 0 || !result) {
            result = false;
            new SnackBar(relativeLayout, "Please Enter the Required Fields.");
        }

        return result;
    }

    private void addView() {

        View view = getLayoutInflater().inflate(R.layout.row_add_teach_details, null, false);

        ImageView imageClose = view.findViewById(R.id.remove_row);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(view);
            }
        });

        classDetailsList.addView(view);
    }

    private void removeView(View view) {
        classDetailsList.removeView(view);
    }

    private void dataObserver() {
        teacherRegisterManualViewModel = ViewModelProviders.of(this).get(TeacherRegisterManualViewModel.class);
        LiveData<String> message = teacherRegisterManualViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "teacher_created":
                        new SnackBar(relativeLayout, "Teacher Registered");
                        TeacherFragment fragment = new TeacherFragment();
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

    private void registerTeacher() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Bundle bundle = this.getArguments();

        String teacherName = bundle.getString("teacherName");
        String teacherAge = bundle.getString("teacherAge");
        String teacherDesignation = bundle.getString("teacherDesignation");
        String teacherCode = bundle.getString("teacherCode");
        String teacherGender = bundle.getString("teacherGender");
        String email = bundle.getString("email");
        String mobile = bundle.getString("mobile");
        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        teacherRegisterManualViewModel.register(teacherName, teacherAge, teacherDesignation, teacherCode, teacherGender, email, mobile,
                list, orgCode);
    }
}