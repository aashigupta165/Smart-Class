package com.education.smartclass.modules.Organisation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.models.ClassCSVSampleData;
import com.education.smartclass.modules.Organisation.model.ClassRegisterFileViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ClassFragment extends Fragment {

    private TextView status, manual_entry, heading, submitbtn;
    private ImageButton fileUpload;

    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;
    private ClassRegisterFileViewModel registerFileViewModel;

    private ArrayList<ClassCSVSampleData> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organisation_teacher_register, container, false);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        manual_entry = view.findViewById(R.id.manual_entry);
        heading = view.findViewById(R.id.heading);
        fileUpload = view.findViewById(R.id.file_upload);
        submitbtn = view.findViewById(R.id.submitbtn);
        status = view.findViewById(R.id.status);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        heading.setText("Add Classes");

        buttonClickEvents();
        dataObserver();

        return view;
    }

    private void buttonClickEvents() {

        fileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });

        manual_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualClassRegisterFragment1 fragment = new ManualClassRegisterFragment1();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClass();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            ContentResolver contentResolver = getContext().getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            if (!mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)).equals("csv")) {
                new SnackBar(relativeLayout, "Please upload csv file!");
                return;
            }

            try {
                list = new ArrayList<>();

                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

                String line = "";
                bufferedReader.readLine();

                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(",");

                    ClassCSVSampleData newSample = new ClassCSVSampleData();
                    newSample.setSno(tokens[0]);
                    newSample.setOrgClass(tokens[1]);
                    if (tokens[2].equals("")) {
                        newSample.setOrgSection("A");
                    } else {
                        newSample.setOrgSection(tokens[2]);
                    }
                    newSample.setSubjects(tokens[3]);

                    list.add(newSample);
                }
                new SnackBar(relativeLayout, "File Uploaded Successfully!");
                status.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                new SnackBar(relativeLayout, "There is an error in uploading file!");
            } catch (Exception e) {
                new SnackBar(relativeLayout, "Please Upload Correct File!");
            }
        }
    }

    private void dataObserver() {

        registerFileViewModel = ViewModelProviders.of(this).get(ClassRegisterFileViewModel.class);
        LiveData<String> message = registerFileViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "class_created":
                        new SnackBar(relativeLayout, "Classes Added");
                        status.setVisibility(View.GONE);
                        break;
                    case "invalid_entry":
                        new SnackBar(relativeLayout, "Invalid Details or Redundant Data Found!");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void registerClass() {

        if (list == null) {
            new SnackBar(relativeLayout, "Please upload file");
            return;
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        registerFileViewModel.register(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), list);
    }
}