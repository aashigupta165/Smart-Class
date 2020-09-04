package com.education.smartclass.roles.Organisation.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.roles.Organisation.model.ClassListViewModel;
import com.education.smartclass.roles.Organisation.model.StudentRegisterManualViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.Logout;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.HashSet;

public class ManualStudentRegisterFragment2 extends Fragment {

    private EditText studentName, studentRollNo, teacherDesignation, studentFather;
    private AutoCompleteTextView studentClass, studentSection;
    private ImageView class_dropdown, section_dropdown;
    private RadioGroup studentGender;
    private RadioButton radioButton;
    private String gender = "male";
    private TextView submitbtn, heading;

    private ClassListViewModel classListViewModel;

    private ArrayList<OrgClassList> orgClassLists;

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
        View view = inflater.inflate(R.layout.fragment_manual_teacher_register2, container, false);

        class_section = view.findViewById(R.id.student_Class_Section);
        class_section.setVisibility(View.VISIBLE);
        studentName = view.findViewById(R.id.teacher_name);
        studentRollNo = view.findViewById(R.id.teacher_age);
        teacherDesignation = view.findViewById(R.id.teacher_designation);
        teacherDesignation.setVisibility(View.INVISIBLE);
        studentClass = view.findViewById(R.id.student_Class);
        studentSection = view.findViewById(R.id.student_Section);
        studentFather = view.findViewById(R.id.teacher_code);
        studentGender = view.findViewById(R.id.teacher_gender);
        submitbtn = view.findViewById(R.id.nextbtn);
        heading = view.findViewById(R.id.heading);
        class_dropdown = view.findViewById(R.id.class_drop_down);
        class_dropdown.setVisibility(View.VISIBLE);
        section_dropdown = view.findViewById(R.id.section_drop_down);
        section_dropdown.setVisibility(View.VISIBLE);

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

        progressDialog.setMessage("Data Searching...");
        progressDialog.show();

        buttonClickEvents();
        dataObserver();
        fetchData();
        textSelectors(view);

        return view;
    }

    private void fetchData() {
        classListViewModel.fetchStudentList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Class");
    }

    private void textSelectors(View view) {

        class_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentClass.showDropDown();
                studentSection.setText("");
            }
        });

        section_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentClass.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Class!");
                    return;
                } else {
                    ArrayList<String> sections = new ArrayList<>();
                    int i = 0;
                    for (OrgClassList s : orgClassLists) {
                        if (s.getOrgClass().equals(studentClass.getText().toString())) {
                            sections.add(i, s.getOrgSection());
                            i++;
                        }
                    }
                    sections = new ArrayList<String>(new HashSet<String>(sections));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sections);
                    studentSection.setAdapter(adapter);
                    studentSection.showDropDown();
                }
            }
        });
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
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
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
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void registerStudent() {

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

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Bundle bundle = this.getArguments();

        String email = bundle.getString("email");
        String mobile = bundle.getString("mobile");
        String dob = bundle.getString("dob");
        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        studentRegisterManualViewModel.register(StudentName, StudentRollNo, StudentClass, StudentSection, StudentFather, email, mobile, dob,
                gender, orgCode);
    }

    private void fetchList() {

        LiveData<ArrayList<OrgClassList>> list = classListViewModel.getList();

        list.observe(this, new Observer<ArrayList<OrgClassList>>() {
            @Override
            public void onChanged(ArrayList<OrgClassList> classLists) {

                orgClassLists = classLists;

                ArrayList<String> classes = new ArrayList<>();
                int i = 0;
                for (OrgClassList s : orgClassLists) {
                    classes.add(i, s.getOrgClass());
                    i++;
                }
                classes = new ArrayList<String>(new HashSet<String>(classes));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, classes);
                studentClass.setAdapter(adapter);
            }
        });
    }
}
