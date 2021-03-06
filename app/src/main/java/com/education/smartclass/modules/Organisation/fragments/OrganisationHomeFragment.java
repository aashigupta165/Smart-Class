package com.education.smartclass.modules.Organisation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.smartclass.R;
import com.education.smartclass.modules.teacher.fragments.TeacherHomeFragment;

public class OrganisationHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        TeacherHomeFragment teacherHomeFragment = new TeacherHomeFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherHomeFragment).commit();

        return view;
    }
}