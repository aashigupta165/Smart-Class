package com.education.smartclass.roles.Organisation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.smartclass.R;
import com.education.smartclass.models.TeacherCSVSampleData;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterFileViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TeacherFragment extends Fragment {

    private TextView status, manual_entry, submitbtn;
    private ImageButton fileUpload;

    private RelativeLayout relativeLayout;
    private TeacherRegisterFileViewModel registerFileViewModel;
    private ProgressDialog progressDialog;

    private ArrayList<TeacherCSVSampleData> list;

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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTeachers();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            sample samples;
            list = new ArrayList<>();

//            String path = data.getData().getPath();
//            File file = new File(path);

            InputStream inputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            try {
                bufferedReader.readLine();
//                        File newFile = new File(path);
//                        newFile.mkdirs();
//                        String csv = path;
//                        CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, true));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    TeacherCSVSampleData samplenew = new TeacherCSVSampleData();
                    samplenew.setSno(tokens[0]);
                    samplenew.setName(tokens[1]);
                    samplenew.setAge(tokens[2]);
                    samplenew.setDesignation(tokens[3]);
                    samplenew.setCode(tokens[4]);
                    samplenew.setGender(tokens[5]);
                    samplenew.setRole(tokens[6]);
                    samplenew.setEmail(tokens[7]);
                    samplenew.setMobile(tokens[8]);
                    samplenew.setClass_section_subject(tokens[9]);

//                            String row[] = new String[]{tokens[0], tokens[1], tokens[2]};
//                            csvWriter.writeNext(row);
                    list.add(samplenew);
                    Toast.makeText(getContext(), samplenew.toString(), Toast.LENGTH_LONG).show();

                }
//                        csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


//            String path = data.getData().getPath();
//            File file = new File(path);
//            Toast.makeText(getContext(), file.getName(), Toast.LENGTH_LONG).show();
//            FileInputStream fileInputStream = null;
//
//            byte[] bytesArray = null;
//            try {
//                bytesArray = new byte[(int) file.length()];
//
//                //read file into bytes[]
//                fileInputStream = new FileInputStream(file);
//                fileInputStream.read(bytesArray);
//                Toast.makeText(getContext(), fileInputStream.read(), Toast.LENGTH_LONG).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (fileInputStream != null) {
//                    try {
//                        fileInputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

//            }
//            Path path = Paths.get("C:\temp\\test2.txt");

//            Files files = Files.copy(path, bytesArray);
//            Files.write(path, bytesArray);
//            Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
//            System.out.println("Done");
//            for (int i = 0; i < bytesArray.length; i++) {
//                Toast.makeText(getContext(), bytesArray[i], Toast.LENGTH_LONG).show();
//                System.out.print((char) bytesArray[i]);
//            }
            //init array with file length
//            byte[] bytesArray = new byte[(int) file.length()];


//            try {
//                FileInputStream fis = new FileInputStream(file);
////                fis.read(bytesArray); //read file into bytes[]
//
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            ReadByteArrayFromFile(Server.MapPath(path);
//            Toast.makeText(getContext(), path, Toast.LENGTH_LONG).show();
//
//            File file = new File(path);
//            .createFile();
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), bytesArray);
//            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//            RequestBody filename = RequestBody.create(file.getName(),MediaType.parse("text/plain"));
//            registerFileViewModel.register(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),fileToUpload);
        }
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
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                        new SnackBar(relativeLayout, s);
                }
            }
        });
    }

    private void registerTeachers() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

//        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        registerFileViewModel.register(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),list);
//        registerFileViewModel.register(orgCode, file);
    }
}