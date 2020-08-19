package com.education.smartclass.roles.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.roles.student.fragments.StudentQuestionaireFragment;
import com.education.smartclass.roles.teacher.fragments.TeacherQuestionaireFragment;
import com.education.smartclass.storage.SharedPrefManager;

public class QuestionaireSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);

        switch (SharedPrefManager.getInstance(getContext()).getUser().getRole()) {
            case "Teacher":
                TeacherQuestionaireFragment teacherQuestionaireFragment = new TeacherQuestionaireFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teacherQuestionaireFragment).commit();
                break;
            case "Student":
                StudentQuestionaireFragment studentQuestionaireFragment = new StudentQuestionaireFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, studentQuestionaireFragment).commit();
                break;
        }
        return view;
    }
}
