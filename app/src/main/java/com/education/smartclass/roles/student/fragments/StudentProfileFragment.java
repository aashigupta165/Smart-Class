package com.education.smartclass.roles.student.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.TeacherClassListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.response.FetchSubjectList;
import com.education.smartclass.roles.student.model.FetchSubjectsViewModel;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class StudentProfileFragment extends Fragment {

    private TextView name, rollno, class_section, dob, email, mobile;
    private ListView listView;

    private ProgressBar progressBar;

    private FetchSubjectsViewModel fetchSubjectsViewModel;

    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        name = view.findViewById(R.id.student_name);
        rollno = view.findViewById(R.id.student_rollno);
        class_section = view.findViewById(R.id.student_class);
        dob = view.findViewById(R.id.student_dob);
        email = view.findViewById(R.id.student_email);
        mobile = view.findViewById(R.id.student_mobile);
        listView = view.findViewById(R.id.subjects);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        name.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentName());
        rollno.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo());
        class_section.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentClass() + " " +
                SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
        dob.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentDOB());
        email.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentEmail());
        mobile.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentMobile());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataObserver();

        fetchSubjectsViewModel.fetchSubjectList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
    }

    private void dataObserver() {

        fetchSubjectsViewModel = ViewModelProviders.of(this).get(FetchSubjectsViewModel.class);
        LiveData<String> message = fetchSubjectsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "subject_found":
                        fetchList();
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
    }

    private void fetchList() {
        LiveData<ArrayList<String>> list = fetchSubjectsViewModel.getList();

        list.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> subjects) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, subjects);
                listView.setAdapter(arrayAdapter);
            }
        });
    }
}