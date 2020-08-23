package com.education.smartclass.roles.Organisation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

public class ManualStudentRegisterFragment1 extends Fragment {

    private EditText email, mobile, dob;
    private TextView nextbtn, heading;

    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
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
        View view = inflater.inflate(R.layout.activity_manual_teacher_register1, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        dob = view.findViewById(R.id.dob);
        dob.setVisibility(View.VISIBLE);
        nextbtn = view.findViewById(R.id.nextbtn);
        heading = view.findViewById(R.id.heading);

        heading.setText("Create Student");
        email.setHint("Student Email");
        mobile.setHint("Student Mobile");

        buttonClickEvents();

        return view;
    }

    private void buttonClickEvents() {

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String Email = email.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();
        String Dob = dob.getText().toString().trim();

        if (Email.isEmpty() || Mobile.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details");
            return;
        }

        if (!Email.matches(emailPattern) || Mobile.length() != 10 || Long.parseLong(Mobile) < Long.parseLong("6000000000")) {
            new SnackBar(relativeLayout, "Invalid Credentials");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("email", Email);
        bundle.putString("mobile", Mobile);
        bundle.putString("dob", Dob);

        ManualStudentRegisterFragment2 fragment = new ManualStudentRegisterFragment2();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}
