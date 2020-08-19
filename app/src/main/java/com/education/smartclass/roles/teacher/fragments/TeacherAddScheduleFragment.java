package com.education.smartclass.roles.teacher.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.education.smartclass.Adapter.TeacherListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.models.TeacherSubjects;
import com.education.smartclass.models.Teachers;
import com.education.smartclass.roles.Organisation.fragments.StudentFragment;
import com.education.smartclass.roles.Organisation.model.StudentRegisterManualViewModel;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.roles.teacher.model.ScheduleAddViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.List;

public class TeacherAddScheduleFragment extends Fragment {

    private AutoCompleteTextView subject, className, section;
    private ImageView subjectDropdown, classDropdown, sectionDropdown;

    private RelativeLayout relativeLayout;

    private ScheduleAddViewModel scheduleAddViewModel;
    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_add_schedule, container, false);

        subject = view.findViewById(R.id.subject);
        className = view.findViewById(R.id.student_Class);
        section = view.findViewById(R.id.student_Section);
        subjectDropdown = view.findViewById(R.id.subject_drop_down);
        classDropdown = view.findViewById(R.id.class_drop_down);
        sectionDropdown = view.findViewById(R.id.section_drop_down);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        fetchData();
        buttonClickEvents();
        dataObserver();

        return view;
    }

    private void dataObserver() {

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> message = fetchDropdownDetailsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                switch (s) {
                    case "loggedIn":
                        setDropdown();
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

    private void buttonClickEvents() {

    }

    private void fetchData() {

        String mobile = SharedPrefManager.getInstance(getContext()).getUser().getTeacherMobile();
        String password = SharedPrefManager.getInstance(getContext()).getPassword();

        fetchDropdownDetailsViewModel.fetchDropdoenDetails(getContext(), mobile, password);
    }

    private void setDropdown() {

        LiveData<List<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(this, new Observer<List<TeacherClasses>>() {
            @Override
            public void onChanged(List<TeacherClasses> teacherClasses) {
                ArrayAdapter<TeacherClasses> adapter = new ArrayAdapter<TeacherClasses>(getContext(), android.R.layout.simple_dropdown_item_1line, (List<TeacherClasses>) list);
                subject.setAdapter(adapter);
            }

//            @Override
//            public void onChanged(ArrayList<Teachers> teachers) {
//
//                ArrayAdapter<TeacherClasses> adapter = new ArrayAdapter<TeacherClasses>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
//                subject.setAdapter(adapter);
////                                list.setLayoutManager(new LinearLayoutManager(getContext()));
////                TeacherListAdapter teacherListAdapter = new TeacherListAdapter(getContext(), teachers);
////                teacher_list.setAdapter(teacherListAdapter);
//            }
        });
    }
}