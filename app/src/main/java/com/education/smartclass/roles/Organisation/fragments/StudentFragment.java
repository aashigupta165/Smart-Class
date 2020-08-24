package com.education.smartclass.roles.Organisation.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.education.smartclass.models.StudentCSVSampleData;
import com.education.smartclass.models.TeacherCSVSampleData;
import com.education.smartclass.roles.Organisation.model.ClassRegisterFileViewModel;
import com.education.smartclass.roles.Organisation.model.StudentRegisterFileViewModel;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterFileViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class StudentFragment extends Fragment {

    private TextView manual_entry, heading, status, submitbtn;
    private ImageButton fileUpload;

    private RelativeLayout relativeLayout;
    private StudentRegisterFileViewModel registerFileViewModel;
    private ProgressDialog progressDialog;

    private ArrayList<StudentCSVSampleData> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);

        manual_entry = view.findViewById(R.id.manual_entry);
        heading = view.findViewById(R.id.heading);
        status = view.findViewById(R.id.status);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        fileUpload = view.findViewById(R.id.file_upload);
        submitbtn = view.findViewById(R.id.submitbtn);

        progressDialog = new ProgressDialog(getContext());

        heading.setText("Create Student");

        buttonClickEvents();
        dataObserver();

        return view;
    }

    private void buttonClickEvents() {

        manual_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualStudentRegisterFragment1 fragment = new ManualStudentRegisterFragment1();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });

        fileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudents();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                list = new ArrayList<>();

                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

                String line = "";
                bufferedReader.readLine();

                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(",");

                    StudentCSVSampleData newSample = new StudentCSVSampleData();
                    newSample.setSno(tokens[0]);
                    newSample.setName(tokens[1]);
                    newSample.setRollNo(tokens[2]);
                    newSample.setStudentClass(tokens[3]);
                    newSample.setStudentSection(tokens[4]);
                    newSample.setFatherName(tokens[5]);
                    newSample.setRole(tokens[6]);
                    newSample.setEmail(tokens[7]);
                    newSample.setMobile(tokens[8]);
                    newSample.setDob(tokens[9]);
                    newSample.setGender(tokens[10]);

                    list.add(newSample);
                }
                new SnackBar(relativeLayout, "File Uploaded Successfully!");
                status.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                new SnackBar(relativeLayout, "There is an error in uploading file!");
            }
        }
    }

    private void dataObserver() {

        registerFileViewModel = ViewModelProviders.of(this).get(StudentRegisterFileViewModel.class);
        LiveData<String> message = registerFileViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "students_added":
                        new SnackBar(relativeLayout, "Studnts Added");
                        status.setVisibility(View.GONE);
                        break;
                    case "invalid_entry":
                        new SnackBar(relativeLayout, "Invalid Details or Redundant Data Found!");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void registerStudents() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        registerFileViewModel.register(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), list);
    }
}