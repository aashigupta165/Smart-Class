package com.education.smartclass.roles.Organisation.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterFileViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

public class TeacherFragment extends Fragment {

    private TextView status, manual_entry, submitbtn;
    private ImageButton fileUpload;

    private RelativeLayout relativeLayout;
    private TeacherRegisterFileViewModel registerFileViewModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        status = view.findViewById(R.id.status);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        manual_entry = view.findViewById(R.id.manual_entry);
        fileUpload = view.findViewById(R.id.file_upload);
        submitbtn = view.findViewById(R.id.submitbtn);

        progressDialog = new ProgressDialog(getContext());

        buttonClickEvents();
        dataObserver();

        return view;
    }

    private void buttonClickEvents() {

        manual_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualTeacherRegisterFragment1 fragment = new ManualTeacherRegisterFragment1();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });

        fileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                registerTeachers();
            }
        });
    }

    private void dataObserver() {

        registerFileViewModel = ViewModelProviders.of(this).get(TeacherRegisterFileViewModel.class);
        LiveData<String> message = registerFileViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "teachers_added":
                        new SnackBar(relativeLayout, "Teachers Added");
                        status.setVisibility(View.GONE);
                        break;
                    case "invalid_entry":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });
    }

    private void registerTeachers() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

//        registerFileViewModel.register(orgCode, file);
    }
}