package com.education.smartclass.roles.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.fragments.OrganisationScheduleFragment;
import com.education.smartclass.roles.student.fragments.StudentScheduleFragment;
import com.education.smartclass.roles.teacher.fragments.TeacherScheduleFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class ScheduleSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Organisation":
                OrganisationScheduleFragment organisationScheduleFragment = new OrganisationScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, organisationScheduleFragment).commit();
                break;
            case "Teacher":
                TeacherScheduleFragment teacherScheduleFragment = new TeacherScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherScheduleFragment).commit();
                break;
            case "Student":
                StudentScheduleFragment studentScheduleFragment = new StudentScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, studentScheduleFragment).commit();
                break;
        }
        return view;
    }
}
