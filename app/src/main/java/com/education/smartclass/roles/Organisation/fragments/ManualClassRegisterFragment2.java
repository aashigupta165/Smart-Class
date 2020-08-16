package com.education.smartclass.roles.Organisation.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.education.smartclass.roles.Organisation.model.ClassRegisterManualViewModel;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterManualViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class ManualClassRegisterFragment2 extends Fragment {

    private LinearLayout subjectList;
    private TextView heading, addbtn, submitbtn;

    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;

    ArrayList<String> list = new ArrayList<>();

    private ClassRegisterManualViewModel classRegisterManualViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manual_teacher_register3, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        subjectList = view.findViewById(R.id.class_details_list);
        addbtn = view.findViewById(R.id.addbtn);
        submitbtn = view.findViewById(R.id.submitbtn);
        heading = view.findViewById(R.id.heading);

        heading.setText("Add Subjects");

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
                    registerSubjects();
                }
            }
        });
    }

    private boolean checkIfValidAndRead() {

        list.clear();
        boolean result = true;

        Bundle bundle = this.getArguments();

        String className = bundle.getString("class");
        String section = bundle.getString("section");

        String temp = className + "_" + section + "_";

        for (int i = 0; i < subjectList.getChildCount(); i++) {
            View view = subjectList.getChildAt(i);

            EditText subject = view.findViewById(R.id.subject);

            if (!subject.getText().toString().equals("")) {
                temp += subject.getText().toString() + "_";
            } else {
                result = false;
                break;
            }

        }

        list.add(temp);

        if (list.size() == 0 || !result) {
            result = false;
            new SnackBar(relativeLayout, "Please Enter the Required Fields.");
        }

        return result;
    }

    private void addView() {

        View view = getLayoutInflater().inflate(R.layout.subject_list_row, null, false);

        ImageView imageClose = view.findViewById(R.id.remove_row);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(view);
            }
        });

        subjectList.addView(view);
    }

    private void removeView(View view) {
        subjectList.removeView(view);
    }

    private void dataObserver() {
        classRegisterManualViewModel = ViewModelProviders.of(this).get(ClassRegisterManualViewModel.class);
        LiveData<String> message = classRegisterManualViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "class_created":
                        new SnackBar(relativeLayout, "Class Created");
                        ClassFragment fragment = new ClassFragment();
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

    private void registerSubjects() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        classRegisterManualViewModel.register(list, orgCode);
    }
}