package com.education.smartclass.roles.Organisation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.smartclass.R;

public class ClassFragment extends Fragment {

    private TextView manual_entry, heading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);

        manual_entry = view.findViewById(R.id.manual_entry);
        heading = view.findViewById(R.id.heading);

        heading.setText("Add Subjects");

        buttonClickEvents();

        return view;
    }

    private void buttonClickEvents() {

        manual_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualClassRegisterFragment1 fragment = new ManualClassRegisterFragment1();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
    }
}