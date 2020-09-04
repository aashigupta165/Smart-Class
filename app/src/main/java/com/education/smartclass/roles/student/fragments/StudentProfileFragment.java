package com.education.smartclass.roles.student.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.R;
import com.education.smartclass.roles.student.model.FetchSubjectsViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.Logout;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StudentProfileFragment extends Fragment {

    private TextView name, rollno, class_section, dob, email, mobile, orgName, subjectsList;
    private ImageView orgLogo;

    private FetchSubjectsViewModel fetchSubjectsViewModel;

    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        orgName = view.findViewById(R.id.org_Name);
        orgLogo = view.findViewById(R.id.org_Logo);
        name = view.findViewById(R.id.student_name);
        rollno = view.findViewById(R.id.student_rollno);
        class_section = view.findViewById(R.id.student_class);
        dob = view.findViewById(R.id.student_dob);
        email = view.findViewById(R.id.student_email);
        mobile = view.findViewById(R.id.student_mobile);
        subjectsList = view.findViewById(R.id.subjects);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        orgName.setText(SharedPrefManager.getInstance(getContext()).getUser().getOrgName());
        name.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentName());
        rollno.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo());
        class_section.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentClass() + " " +
                SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
        dob.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentDOB());
        email.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentEmail());
        mobile.setText(SharedPrefManager.getInstance(getContext()).getUser().getStudentMobile());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        URL url = new URL(SharedPrefManager.getInstance(getContext()).getUser().getOrgLogo());
                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        orgLogo.setImageBitmap(bitmap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);

        dataObserver();

        fetchSubjectsViewModel.fetchSubjectList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
    }

    private void dataObserver() {

        fetchSubjectsViewModel = ViewModelProviders.of(this).get(FetchSubjectsViewModel.class);
        LiveData<String> message = fetchSubjectsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "subject_found":
                        fetchList();
                        break;
                    case "no_data":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });
    }

    private void fetchList() {
        LiveData<ArrayList<String>> list = fetchSubjectsViewModel.getList();

        list.observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> subjects) {
                String Subjects = "";
                for (String sub : subjects) {
                    if (!sub.equals("")) {
                        Subjects += sub + ", ";
                    }
                }
                Subjects = Subjects.substring(0, Subjects.length() - 2);
                subjectsList.setText(Subjects);
            }
        });
    }
}