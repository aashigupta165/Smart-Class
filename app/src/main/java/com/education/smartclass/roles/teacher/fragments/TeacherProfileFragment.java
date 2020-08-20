package com.education.smartclass.roles.teacher.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.TeacherClassListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class TeacherProfileFragment extends Fragment {

    private TextView name, designation, id, email, mobile;
    private RecyclerView detailList;

    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;

    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        name = view.findViewById(R.id.teacher_name);
        designation = view.findViewById(R.id.teacher_designation);
        id = view.findViewById(R.id.teacher_code);
        email = view.findViewById(R.id.teacher_email);
        mobile = view.findViewById(R.id.teacher_mobile);
        detailList = view.findViewById(R.id.detailsList);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        name.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherName());
        designation.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherDesignation());
        id.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
        email.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherEmail());
        mobile.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherMobile());

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataObserver();

        fetchDropdownDetailsViewModel.fetchDropdownDetails(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
    }

    private void dataObserver() {

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> message = fetchDropdownDetailsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "teacher_detail_found":
                        fetchList();
                        break;
                    case "Invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Invalid_teacherCode":
                        new SnackBar(relativeLayout, "Invalid Account");
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
        LiveData<ArrayList<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(this, new Observer<ArrayList<TeacherClasses>>() {
            @Override
            public void onChanged(ArrayList<TeacherClasses> teacherClasses) {
                detailList.setLayoutManager(new LinearLayoutManager(getContext()));
                TeacherClassListAdapter teacherClassListAdapter = new TeacherClassListAdapter(getContext(), teacherClasses);
                detailList.setAdapter(teacherClassListAdapter);
            }
        });
    }
}