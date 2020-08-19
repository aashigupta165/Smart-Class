package com.education.smartclass.roles.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.roles.student.fragments.StudentAddScheduleFragment;
import com.education.smartclass.roles.teacher.fragments.TeacherAddScheduleFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class AddScheduleSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Teacher":
                TeacherAddScheduleFragment teacherAddScheduleFragment = new TeacherAddScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherAddScheduleFragment).commit();
                break;
            case "Student":
                StudentAddScheduleFragment studentAddScheduleFragment = new StudentAddScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, studentAddScheduleFragment).commit();
                break;
        }
        return view;
    }
}
