package com.education.smartclass.roles.teacher.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.models.TeacherSubjects;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.roles.teacher.model.PostQuestionViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class TeacherAddQuestionFragment extends Fragment {

    private AutoCompleteTextView subject, className, section;
    private ImageView subjectDropdown, classDropdown, sectionDropdown;
    private EditText question;
    private TextView submitbtn;
    private RadioGroup topic;
    private RadioButton radioButton, subjectradiobtn;

    private RelativeLayout relativeLayout;

    private ArrayList<TeacherClasses> teacherClassesArrayList = new ArrayList<TeacherClasses>();

    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;
    private PostQuestionViewModel postQuestionViewModel;

    private ProgressDialog progressDialog;

    private String topicOptionSelected = "Subject";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_add_question, container, false);

        subject = view.findViewById(R.id.subject);
        className = view.findViewById(R.id.student_Class);
        section = view.findViewById(R.id.student_Section);
        subjectDropdown = view.findViewById(R.id.subject_drop_down);
        classDropdown = view.findViewById(R.id.class_drop_down);
        sectionDropdown = view.findViewById(R.id.section_drop_down);
        topic = view.findViewById(R.id.topic);
        question = view.findViewById(R.id.question);
        subjectradiobtn = view.findViewById(R.id.subjectbtn);
        submitbtn = view.findViewById(R.id.submitbtn);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Data Searching");
        progressDialog.show();

        dataObserver();
        fetchData();
        TextSelector(view);
        buttonClickEvents();

        return view;
    }

    private void TextSelector(View view) {

        classDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                className.showDropDown();
                section.setText("");
                subject.setText("");
                subjectradiobtn.setChecked(true);
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
                    for (TeacherClasses s : teacherClassesArrayList) {
                        if (s.getTeacherClass().equals(className.getText().toString())) {
                            sections.add(i, s.getTeacherSection());
                            i++;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sections);
                    section.setAdapter(adapter);
                    section.showDropDown();
                    subject.setText("");
                    subjectradiobtn.setChecked(true);
                }
            }
        });

        topic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);

                topicOptionSelected = radioButton.getText().toString();

                if (radioButton.getText().toString().equals("Other")) {
                    if (className.getText().toString().equals("") && section.getText().toString().equals("")) {
                        new SnackBar(relativeLayout, "Fill Above Details First!");
                        subjectradiobtn.setChecked(true);
                        return;
                    }
                    subject.setText(null);
                    subject.setVisibility(View.GONE);
                    subjectDropdown.setVisibility(View.GONE);
                } else {
                    subject.setVisibility(View.VISIBLE);
                    subjectDropdown.setVisibility(View.VISIBLE);
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
                for (TeacherClasses s : teacherClassesArrayList) {
                    if (s.getTeacherClass().equals(className.getText().toString()) && s.getTeacherSection().equals(section.getText().toString())) {
                        for (TeacherSubjects t : s.getTeachingSubjects())
                            subjects.add(i, t.getSubject());
                        i++;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, subjects);
                subject.setAdapter(adapter);
                subject.showDropDown();
            }
        });
    }

    private void buttonClickEvents() {

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postQuestion();
            }
        });
    }

    private void dataObserver() {

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> message = fetchDropdownDetailsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
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

        postQuestionViewModel = ViewModelProviders.of(this).get(PostQuestionViewModel.class);
        LiveData<String> msg = postQuestionViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s){
                    case "question_asked":
                        TeacherAddQuestionFragment fragment = new TeacherAddQuestionFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        new SnackBar(relativeLayout, "Question Posted");
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

    private void fetchData() {

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();

        fetchDropdownDetailsViewModel.fetchDropdownDetails(orgCode, teacherCode);
    }

    private void setDropdown() {

        LiveData<ArrayList<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<TeacherClasses>>() {
            @Override
            public void onChanged(ArrayList<TeacherClasses> teacherClass) {

                teacherClassesArrayList = teacherClass;
                ArrayList<String> classes = new ArrayList<>();
                int i = 0;
                for (TeacherClasses s : teacherClass) {
                    classes.add(i, s.getTeacherClass());
                    i++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, classes);
                className.setAdapter(adapter);
            }
        });
    }

    private void postQuestion() {

        if (className.getText().toString().equals("") || section.getText().toString().equals("") || question.getText().toString().equals("")) {
            new SnackBar(relativeLayout, "Please Enter All Details!");
            return;
        }

        if (topicOptionSelected.equals("Subject")) {
            if (subject.getText().toString().equals("")) {
                new SnackBar(relativeLayout, "Please fill all the Details!");
                return;
            }
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        postQuestionViewModel.postQuestion(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), topicOptionSelected, subject.getText().toString(),
                question.getText().toString(), SharedPrefManager.getInstance(getContext()).getUser().getTeacherName(),
                SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode(), "Teacher", className.getText().toString(),
                section.getText().toString());
    }
}