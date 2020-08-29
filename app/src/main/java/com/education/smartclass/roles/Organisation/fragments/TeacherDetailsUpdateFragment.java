package com.education.smartclass.roles.Organisation.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.models.TeacherSubjects;
import com.education.smartclass.roles.Organisation.model.TeacherUpdateViewModel;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TeacherDetailsUpdateFragment extends Fragment {

    private LinearLayout classDetailsList;
    private TextView addbtn, submitbtn;

    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;

    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;
    private TeacherUpdateViewModel teacherUpdateViewModel;

    ArrayList<String> list = new ArrayList<>();

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

        progressDialog.setMessage("Data Searching...");
        progressDialog.show();

        buttonClickEvents();
        dataObserver();
        fetchData();

        return view;
    }

    private void fetchData() {

        Bundle bundle = this.getArguments();
        String teacherCode = bundle.getString("teacherCode");

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        fetchDropdownDetailsViewModel.fetchDropdownDetails(orgCode, teacherCode);
    }

    private void buttonClickEvents() {

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (checkIfValidAndRead()) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(submitbtn.getWindowToken(), 0);
                    updateTeacher();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean checkIfValidAndRead() {

        list.clear();
        boolean result = true;

        int isvalid = 0;

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
                temp += "A_";
            }

            if (!Pattern.matches("[a-zA-Z0-9 ]+", subject.getText().toString())) {
                isvalid++;
                result = false;
                break;
            } else if (!subject.getText().toString().equals("")) {
                temp += subject.getText().toString();
            } else {
                result = false;
                break;
            }

            list.add(temp);
        }

        if (list.size() == 0 || !result) {
            result = false;
            if (isvalid != 0) {
                new SnackBar(relativeLayout, "Please enter one valid subject at a time");
            } else {
                new SnackBar(relativeLayout, "Please Enter the Required Fields.");
            }
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
        teacherUpdateViewModel = ViewModelProviders.of(this).get(TeacherUpdateViewModel.class);
        LiveData<String> message = teacherUpdateViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "teacher_updated":
                        new SnackBar(relativeLayout, "Teacher Registered");
                        TeacherListFragment fragment = new TeacherListFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        break;
                    case "invalid_data":
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

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> messages = fetchDropdownDetailsViewModel.getMessage();

        messages.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "teacher_detail_found":
                        setDropdown();
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please try again later!");
                }
            }
        });
    }

    private void setDropdown() {

        LiveData<ArrayList<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<TeacherClasses>>() {
            @Override
            public void onChanged(ArrayList<TeacherClasses> teacherClass) {
                int i = 0;
                for (TeacherClasses s : teacherClass) {
                    for (TeacherSubjects sub : s.getTeachingSubjects()) {
                        addView();
                        View view = classDetailsList.getChildAt(i);

                        EditText className = view.findViewById(R.id.className);
                        EditText section = view.findViewById(R.id.section);
                        EditText subject = view.findViewById(R.id.subject);

                        className.setText(s.getTeacherClass());
                        section.setText(s.getTeacherSection());
                        subject.setText(sub.getSubject());
                        i++;
                    }
                }
            }
        });
    }

    private void updateTeacher() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Bundle bundle = this.getArguments();

        teacherUpdateViewModel.update(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), bundle.getString("teacherCode"), list);
    }
}
