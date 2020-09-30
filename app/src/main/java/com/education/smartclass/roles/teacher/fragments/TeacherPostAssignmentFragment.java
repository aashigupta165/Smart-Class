package com.education.smartclass.roles.teacher.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.models.TeacherSubjects;
import com.education.smartclass.roles.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.roles.teacher.model.PostAssignmentViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TeacherPostAssignmentFragment extends Fragment {

    private AutoCompleteTextView subject, className, section;
    private ImageView subjectDropdown, classDropdown, sectionDropdown, assignment;
    private EditText title, description, fileName;
    private TextView submitbtn;

    private RelativeLayout relativeLayout;

    private ArrayList<TeacherClasses> teacherClassesArrayList = new ArrayList<TeacherClasses>();

    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;
    private PostAssignmentViewModel postAssignmentViewModel;

    private ProgressDialog progressDialog;

    private MultipartBody.Part file;
    private String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_post_assignment, container, false);

        subject = view.findViewById(R.id.subject);
        className = view.findViewById(R.id.student_Class);
        section = view.findViewById(R.id.student_Section);
        subjectDropdown = view.findViewById(R.id.subject_drop_down);
        classDropdown = view.findViewById(R.id.class_drop_down);
        sectionDropdown = view.findViewById(R.id.section_drop_down);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        assignment = view.findViewById(R.id.assignment);
        fileName = view.findViewById(R.id.assignment_heading);
        submitbtn = view.findViewById(R.id.submitbtn);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Data Searching...");
        progressDialog.show();

        dataObserver();
        fetchData();
        TextSelector(view);
        buttonClickEvents();

        return view;
    }

    private void TextSelector(View view) {

        classDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                className.showDropDown();
                section.setText("");
                subject.setText("");
            }
        });

        sectionDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Class!");
                    return;
                } else {
                    ArrayList<String> sections = new ArrayList<>();
                    int i = 0;
                    for (TeacherClasses s : teacherClassesArrayList) {
                        if (s.getTeacherClass().equals(className.getText().toString())) {
                            sections.add(i, s.getTeacherSection());
                            i++;
                        }
                    }
                    sections = new ArrayList<String>(new HashSet<String>(sections));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, sections);
                    section.setAdapter(adapter);
                    section.showDropDown();
                    subject.setText("");
                }
            }
        });

        subjectDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (className.getText().toString().equals("") || section.getText().toString().equals("")) {
                    new SnackBar(relativeLayout, "Please Select Above Fields!");
                    return;
                }

                ArrayList<String> subjects = new ArrayList<>();
                int i = 0;
                for (TeacherClasses s : teacherClassesArrayList) {
                    if (s.getTeacherClass().equals(className.getText().toString()) && s.getTeacherSection().equals(section.getText().toString())) {
                        for (TeacherSubjects t : s.getTeachingSubjects())
                            subjects.add(i, t.getSubject());
                        i++;
                    }
                }
                subjects = new ArrayList<String>(new HashSet<String>(subjects));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, subjects);
                subject.setAdapter(adapter);
                subject.showDropDown();
            }
        });

        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                ContentResolver contentResolver = getContext().getContentResolver();
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                String extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
                String assignmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String assignmentTime = new SimpleDateFormat("HH:mm:SS", Locale.getDefault()).format(new Date());
                String name = SharedPrefManager.getInstance(getContext()).getUser().getTeacherName() + "_" + className.getText().toString() + "_"
                        + section.getText().toString() + "_" + subject.getText().toString() + "_" + assignmentDate + "_" + assignmentTime + ".";
                byte[] byteArray = null;
                if (extension.toLowerCase().equals("pdf")) {
                    type = "pdf";
                    InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    byteArray = new byte[inputStream.available()];
                    inputStream.read(byteArray);
                } else if (extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("png")) {
                    type = "image";
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(getActivity().getContentResolver().openFileDescriptor(data.getData(), "r").getFileDescriptor());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                } else {
                    new SnackBar(relativeLayout, "Please Select Image or PDF File!");
                    return;
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), byteArray);
                file = MultipartBody.Part.createFormData("file", name + extension, requestBody);
                File path = new File(uri.getPath());
                fileName.setText(path.getName());
                new SnackBar(relativeLayout, "File Uploaded Successfully");
            } else {
                new SnackBar(relativeLayout, "You haven't picked File.");
            }
        } catch (Exception e) {
            new SnackBar(relativeLayout, "Something went wrong");
        }
    }

    private void fetchData() {

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();

        fetchDropdownDetailsViewModel.fetchDropdownDetails(orgCode, teacherCode);
    }

    private void setDropdown() {

        LiveData<ArrayList<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<TeacherClasses>>() {
            @Override
            public void onChanged(ArrayList<TeacherClasses> teacherClass) {

                teacherClassesArrayList = teacherClass;
                ArrayList<String> classes = new ArrayList<>();
                int i = 0;
                for (TeacherClasses s : teacherClass) {
                    classes.add(i, s.getTeacherClass());
                    i++;
                }
                classes = new ArrayList<String>(new HashSet<String>(classes));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, classes);
                className.setAdapter(adapter);
            }
        });
    }

    private void dataObserver() {

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> message = fetchDropdownDetailsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "teacher_detail_found":
                        setDropdown();
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please try again later!");
                }
            }
        });

        postAssignmentViewModel = ViewModelProviders.of(this).get(PostAssignmentViewModel.class);
        LiveData<String> msg = postAssignmentViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "assignment_created":
                        TeacherPostAssignmentFragment fragment = new TeacherPostAssignmentFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        new SnackBar(relativeLayout, "Assignment Posted");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please try again later!");
                }
            }
        });
    }

    private void buttonClickEvents() {

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(submitbtn.getWindowToken(), 0);
                postAssignment();
            }
        });
    }

    private void postAssignment() {

        if (className.getText().toString().equals("") || section.getText().toString().equals("") || subject.getText().toString().equals("") ||
                title.getText().toString().equals("") || fileName.getText().toString().equals("")) {
            new SnackBar(relativeLayout, "Please Enter All Details!");
            return;
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        postAssignmentViewModel.postAssignment(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode(),
                title.getText().toString(), description.getText().toString(), className.getText().toString(), section.getText().toString(), subject.getText().toString(),
                type, file);
    }

}