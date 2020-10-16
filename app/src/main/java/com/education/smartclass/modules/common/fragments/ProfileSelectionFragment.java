package com.education.smartclass.modules.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.modules.Organisation.fragments.OrganisationProfileFragment;
import com.education.smartclass.modules.student.fragments.StudentProfileFragment;
import com.education.smartclass.modules.teacher.fragments.TeacherProfileFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class ProfileSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Organisation":
                OrganisationProfileFragment organisationProfileFragment = new OrganisationProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, organisationProfileFragment).commit();
                break;
            case "Teacher":
                TeacherProfileFragment teacherProfileFragment = new TeacherProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherProfileFragment).commit();
                break;
            case "Student":
                StudentProfileFragment studentProfileFragment = new StudentProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, studentProfileFragment).commit();
                break;
        }
        return view;
    }
}
