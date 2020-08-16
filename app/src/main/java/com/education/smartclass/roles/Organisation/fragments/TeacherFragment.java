package com.education.smartclass.roles.Organisation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.smartclass.R;
import com.education.smartclass.roles.Organisation.activities.OrganisationHomeActivity;
import com.education.smartclass.roles.Organisation.model.TeacherRegisterFileViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TeacherFragment extends Fragment {

    private TextView status, manual_entry, submitbtn;
    private ImageButton fileUpload;

    private RelativeLayout relativeLayout;
    private TeacherRegisterFileViewModel registerFileViewModel;
    private ProgressDialog progressDialog;

//    private MultipartBody.Part filef;
//    private String filePath, fileName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);

        if(Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
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
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new ManualTeacherRegisterFragment1());
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        fileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, Uri.parse(MediaStore.Files.FileColumns.DATA));
//                startActivityForResult(galleryIntent, 0);
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.getMediaScannerUri());
//                getActivity().startActivityForResult(galleryIntent, 0);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/*");
                startActivityForResult(Intent.createChooser(intent, "Open CSV"), 0);
//                new MaterialFilePicker()
//                        .withActivity(getActivity())
//                        .withRequestCode(1000)
//                        .withHiddenFiles(true)
//                        .withTitle("Select CSV File")
//                        .start();

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTeachers();
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 1001:{
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_LONG).show();
//
//                }
//            }
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//                Toast.makeText(getContext(), "1", Toast.LENGTH_LONG).show();
//////            File file=new File(path);
//            Uri uri = data.getData();
//            String uriString = uri.toString();
//            File file = new File(uriString);
//            Toast.makeText(getContext(), "2", Toast.LENGTH_LONG).show();
//            CSVReader csvReader=new CSVReader(getContext());
//            Toast.makeText(getContext(), "3", Toast.LENGTH_LONG).show();
//            List csv=csvReader.read(file.getAbsoluteFile());
//            Toast.makeText(getContext(), "4", Toast.LENGTH_LONG).show();
//            if(csv.size()>0){
//                String[] header_row =(String[]) csv.get(0);
//                if(header_row.length>1){
//                    String col1=header_row[0];
//                    String col2=header_row[1];
//                }
//            }
////
//            Toast.makeText(getContext(),csv.size() + " rows", Toast.LENGTH_LONG).show();
////                Toast.makeText(getContext(), "ho gya", Toast.LENGTH_LONG).show();
////                String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
////                Toast.makeText(getContext(), path, Toast.LENGTH_LONG).show();
////                File file = new File(path);
////                if (path != null){
////                    filePath = path;
////                    Toast.makeText(getContext(), path, Toast.LENGTH_LONG).show();
////                }
////                Map<String, RequestBody> map = new HashMap<>();
////                RequestBody requestBody = RequestBody.create(MediaType.parse("application/*"), file);
////                filef = MultipartBody.Part.createFormData("file", "organisationLogo.csv", requestBody);
//                status.setVisibility(View.VISIBLE);
//            } else {
//                new SnackBar(relativeLayout, "You haven't picked Image.");
//            }
//        } catch (Exception e) {
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
//            new SnackBar(relativeLayout, "Something went wrong");
//        }
//        String FileName = Calendar.getInstance().getTimeInMillis() + SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
//    }

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

//        if (filef == null) {
//            new SnackBar(relativeLayout, "Please Upload the Logo");
//            return;
//        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

//        registerFileViewModel.register(orgCode, filef);
    }
}