package com.education.smartclass.roles.Organisation.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.smartclass.R;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.models.OrgSubjects;
import com.education.smartclass.roles.Organisation.model.ClassListViewModel;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterManualViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

public class ManualTeacherRegisterFragment3 extends Fragment {

    private LinearLayout classDetailsList;
    private TextView addbtn, submitbtn;

    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;

    ArrayList<String> list = new ArrayList<>();
    private ArrayList<OrgClassList> orgClassLists;

    private TeacherRegisterManualViewModel teacherRegisterManualViewModel;
    private ClassListViewModel classListViewModel;

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
        classListViewModel.fetchStudentList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Class");
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
                    registerTeacher();
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

        AutoCompleteTextView className = view.findViewById(R.id.className);
        AutoCompleteTextView section = view.findViewById(R.id.section);
        AutoCompleteTextView subject = view.findViewById(R.id.subject);

        ImageView classDropdown = view.findViewById(R.id.class_drop_down);
        ImageView sectionDropdown = view.findViewById(R.id.section_drop_down);
        ImageView subjectDropdown = view.findViewById(R.id.subject_drop_down);

        ArrayList<String> classes = new ArrayList<>();
        int i = 0;
        for (OrgClassList s : orgClassLists) {
            classes.add(i, s.getOrgClass());
            i++;
        }
        classes = new ArrayList<String>(new HashSet<String>(classes));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, classes);
        className.setAdapter(adapter);

        classDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                className.showDropDown();
                section.setText("");
                subject.setText("");
            }
        });

        sectionDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Class!");
                    return;
                } else {
                    ArrayList<String> sections = new ArrayList<>();
                    int i = 0;
                    for (OrgClassList s : orgClassLists) {
                        if (s.getOrgClass().equals(className.getText().toString())) {
                            sections.add(i, s.getOrgSection());
                            i++;
                        }
                    }
                    sections = new ArrayList<String>(new HashSet<String>(sections));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sections);
                    section.setAdapter(adapter);
                    section.showDropDown();
                    subject.setText("");
                }
            }
        });

        subjectDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (className.getText().toString().equals("") || section.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Above Fields!");
                    return;
                }

                ArrayList<String> subjects = new ArrayList<>();
                int i = 0;
                for (OrgClassList s : orgClassLists) {
                    if (s.getOrgClass().equals(className.getText().toString()) && s.getOrgSection().equals(section.getText().toString())) {
                        for (OrgSubjects t : s.getOrgSubjects()) {
                            if (!t.getSubject().equals("")) {
                                subjects.add(i, t.getSubject());
                                i++;
                            }
                        }
                    }
                }
                subjects = new ArrayList<String>(new HashSet<String>(subjects));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, subjects);
                subject.setAdapter(adapter);
                subject.showDropDown();
            }
        });
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

        classListViewModel = ViewModelProviders.of(this).get(ClassListViewModel.class);
        LiveData<String> messages = classListViewModel.getMessage();

        messages.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "list_found":
                        fetchList();
                        break;
                    case "no_data":
                        new SnackBar(relativeLayout, "Please register class!");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void fetchList() {

        LiveData<ArrayList<OrgClassList>> list = classListViewModel.getList();

        list.observe(this, new Observer<ArrayList<OrgClassList>>() {
            @Override
            public void onChanged(ArrayList<OrgClassList> classLists) {
                orgClassLists = classLists;
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