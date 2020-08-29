package com.education.smartclass.roles.Organisation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.education.smartclass.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class BottomNavigationSelector extends Fragment {

    private ChipNavigationBar bottomNav;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_nav, container, false);

        bottomNav = view.findViewById(R.id.bottom_nav);

        if (savedInstanceState == null) {
            bottomNav.setItemSelected(R.id.teacher_list, true);
            fragmentManager = getParentFragmentManager();
            TeacherListFragment teacherListFragment = new TeacherListFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, teacherListFragment).commit();
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.teacher_list:
                        fragment = new TeacherListFragment();
                        break;
                    case R.id.student_list:
                        fragment = new StudentListFragment();
                        break;
                    case R.id.class_list:
                        fragment = new StudentFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            }
        });

        return view;
    }
}
