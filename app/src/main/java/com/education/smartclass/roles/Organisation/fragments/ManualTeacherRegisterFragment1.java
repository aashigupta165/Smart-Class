package com.education.smartclass.roles.Organisation.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

public class ManualTeacherRegisterFragment1 extends Fragment {

    private EditText email, mobile;
    private TextView nextbtn;

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
        View view = inflater.inflate(R.layout.fragment_manual_teacher_register1, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        nextbtn = view.findViewById(R.id.nextbtn);

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

        String Email = email.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();

        if (Email.isEmpty() || Mobile.isEmpty()) {
            new SnackBar(relativeLayout, "Please Enter All The Details");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches() || Mobile.length() != 10 || Long.parseLong(Mobile) < Long.parseLong("6000000000")) {
            new SnackBar(relativeLayout, "Invalid Credentials");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("email", Email);
        bundle.putString("mobile", Mobile);

        ManualTeacherRegisterFragment2 fragment = new ManualTeacherRegisterFragment2();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}