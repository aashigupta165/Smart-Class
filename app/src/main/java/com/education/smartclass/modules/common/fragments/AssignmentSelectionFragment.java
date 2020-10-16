package com.education.smartclass.modules.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.modules.student.fragments.StudentAssignmentFragment;
import com.education.smartclass.modules.teacher.fragments.TeacherAssignmentFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class AssignmentSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Teacher":
                TeacherAssignmentFragment teacherAssignmentFragment = new TeacherAssignmentFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherAssignmentFragment).commit();
                break;
            case "Student":
                StudentAssignmentFragment studentAssignmentFragment = new StudentAssignmentFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, studentAssignmentFragment).commit();
                break;
        }
        return view;
    }
}
