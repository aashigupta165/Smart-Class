package com.education.smartclass.modules.Organisation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

public class ManualClassRegisterFragment1 extends Fragment {

    private EditText className, section;
    private TextView heading, nextbtn;

    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_teacher_register1, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        className = view.findViewById(R.id.email);
        className.setHint("Enter Class");
        section = view.findViewById(R.id.mobile);
        section.setHint("Section");
        nextbtn = view.findViewById(R.id.nextbtn);
        heading = view.findViewById(R.id.heading);

        heading.setText("Add Subjects");

        buttonClickEvents();

        return view;
    }

    private void buttonClickEvents() {

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nextbtn.getWindowToken(), 0);
                checkDetails();
            }
        });
    }

    private void checkDetails() {

        String Class = className.getText().toString().trim();
        String Section = section.getText().toString().trim();

        if (Class.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details");
            return;
        }

        if (Section.equals("")) {
            Section = "A";
        }

        Bundle bundle = new Bundle();
        bundle.putString("class", Class);
        bundle.putString("section", Section);

        ManualClassRegisterFragment2 fragment = new ManualClassRegisterFragment2();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}
