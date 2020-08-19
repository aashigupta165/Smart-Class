package com.education.smartclass.roles.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.fragments.ProfileFragment;
import com.education.smartclass.roles.Organisation.fragments.TeacherFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class ProfileSelectionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()){
            case "Organisation":
                ProfileFragment profileFragment = new ProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profileFragment).commit();
                break;
            case "Teacher":
                TeacherFragment teacherFragment = new TeacherFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherFragment).commit();
                break;
        }
        return view;
    }
}
