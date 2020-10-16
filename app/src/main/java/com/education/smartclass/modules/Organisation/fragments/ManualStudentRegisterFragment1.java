package com.education.smartclass.modules.Organisation.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.education.smartclass.R;
import com.education.smartclass.utils.SnackBar;

import java.util.Calendar;

public class ManualStudentRegisterFragment1 extends Fragment {

    private EditText email, mobile;
    private TextView nextbtn, heading, dob;

    private RelativeLayout relativeLayout;

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);

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

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nextbtn.getWindowToken(), 0);
                checkDetails();
            }
        });
    }

    private void datepicker() {

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String selectedDate = dayOfMonth + "-" + month + "-" + year;
                dob.setText(selectedDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener,
                year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker();
        datePickerDialog.show();
    }

    private void checkDetails() {

        String Email = email.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();
        String Dob = dob.getText().toString().trim();

        if (Email.isEmpty() || Mobile.isEmpty() || Dob.isEmpty()) {
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
        bundle.putString("dob", Dob);

        ManualStudentRegisterFragment2 fragment = new ManualStudentRegisterFragment2();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}
