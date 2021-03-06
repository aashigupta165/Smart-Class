package com.education.smartclass.modules.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.modules.Organisation.fragments.OrganisationHomeFragment;
import com.education.smartclass.modules.student.fragments.StudentHomeFragment;
import com.education.smartclass.modules.teacher.fragments.TeacherHomeFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class HomeSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Organisation":
                OrganisationHomeFragment organisationHomeFragment = new OrganisationHomeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, organisationHomeFragment).commit();
                break;
            case "Teacher":
                TeacherHomeFragment teacherHomeFragment = new TeacherHomeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherHomeFragment).commit();
                break;
            case "Student":
                StudentHomeFragment studentHomeFragment = new StudentHomeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, studentHomeFragment).commit();
                break;
        }
        return view;
    }
}
