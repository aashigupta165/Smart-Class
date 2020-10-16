package com.education.smartclass.modules.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.modules.Organisation.fragments.OrganisationAddScheduleFragment;
import com.education.smartclass.modules.teacher.fragments.TeacherAddScheduleFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class AddScheduleSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Organisation":
                OrganisationAddScheduleFragment organisationAddScheduleFragment = new OrganisationAddScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, organisationAddScheduleFragment).commit();
                break;
            case "Teacher":
                TeacherAddScheduleFragment teacherAddScheduleFragment = new TeacherAddScheduleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherAddScheduleFragment).commit();
                break;
        }
        return view;
    }
}
