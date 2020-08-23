package com.education.smartclass.roles.student.fragments;

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
import com.education.smartclass.roles.student.model.FetchSubjectsViewModel;
import com.education.smartclass.roles.teacher.model.PostQuestionViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class StudentAddQuestionFragment extends Fragment {

    private AutoCompleteTextView subject, className, section;
    private ImageView subjectDropdown, classDropdown, sectionDropdown;
    private EditText question;
    private TextView submitbtn;
    private RadioGroup topic;
    private RadioButton radioButton;

    private RelativeLayout relativeLayout;

    private ArrayList<String> subjectsArrayList = new ArrayList<String>();

    private FetchSubjectsViewModel fetchSubjectsViewModel;
    private PostQuestionViewModel postQuestionViewModel;

    private ProgressDialog progressDialog;

    private String topicOptionSelected = "Subject";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_add_question, container, false);

        subject = view.findViewById(R.id.subject);
        className = view.findViewById(R.id.student_Class);
        className.setVisibility(View.GONE);
        section = view.findViewById(R.id.student_Section);
        section.setVisibility(View.GONE);
        subjectDropdown = view.findViewById(R.id.subject_drop_down);
        classDropdown = view.findViewById(R.id.class_drop_down);
        classDropdown.setVisibility(View.GONE);
        sectionDropdown = view.findViewById(R.id.section_drop_down);
        sectionDropdown.setVisibility(View.GONE);
        topic = view.findViewById(R.id.topic);
        question = view.findViewById(R.id.question);
        submitbtn = view.findViewById(R.id.submitbtn);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Data Searching...");
        progressDialog.show();

        dataObserver();
        fetchData();
        TextSelector(view);
        buttonClickEvents();

        return view;
    }

    private void TextSelector(View view) {

        topic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);

                topicOptionSelected = radioButton.getText().toString();

                if (radioButton.getText().toString().equals("Other")) {
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

        fetchSubjectsViewModel = ViewModelProviders.of(this).get(FetchSubjectsViewModel.class);
        LiveData<String> message = fetchSubjectsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "subject_found":
                        setDropdown();
                        break;
                    case "no_data":
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

        postQuestionViewModel = ViewModelProviders.of(this).get(PostQuestionViewModel.class);
        LiveData<String> msg = postQuestionViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "question_asked":
                        StudentAddQuestionFragment fragment = new StudentAddQuestionFragment();
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

        fetchSubjectsViewModel.fetchSubjectList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
    }

    private void setDropdown() {

        LiveData<ArrayList<String>> list = fetchSubjectsViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> subjects) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, subjects);
                subject.setAdapter(adapter);
            }
        });
    }

    private void postQuestion() {

        if (topicOptionSelected.equals("Subject")) {
            if (subject.getText().toString().equals("")) {
                new SnackBar(relativeLayout, "Please fill all the Details!");
                return;
            }
        }

        if (question.getText().toString().equals("")) {
            new SnackBar(relativeLayout, "Please Enter All Details!");
            return;
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        postQuestionViewModel.postQuestion(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), topicOptionSelected, subject.getText().toString(),
                question.getText().toString(), SharedPrefManager.getInstance(getContext()).getUser().getStudentName(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo(), "Student",
                SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
    }
}