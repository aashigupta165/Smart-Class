package com.education.smartclass.modules.teacher.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.TeacherClassListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.modules.teacher.model.FetchDropdownDetailsViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TeacherProfileFragment extends Fragment {

    private TextView orgName, name, designation, id, email, mobile, no_data;
    private ImageView orgLogo;
    private RecyclerView detailList;

    private ProgressBar progressBar;

    private FetchDropdownDetailsViewModel fetchDropdownDetailsViewModel;

    private RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        orgName = view.findViewById(R.id.org_Name);
        name = view.findViewById(R.id.teacher_name);
        designation = view.findViewById(R.id.teacher_designation);
        id = view.findViewById(R.id.teacher_code);
        email = view.findViewById(R.id.teacher_email);
        mobile = view.findViewById(R.id.teacher_mobile);
        detailList = view.findViewById(R.id.detailsList);
        no_data = view.findViewById(R.id.no_data);
        orgLogo = view.findViewById(R.id.org_Logo);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        orgName.setText(SharedPrefManager.getInstance(getContext()).getUser().getOrgName());
        name.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherName());
        designation.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherDesignation());
        id.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
        email.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherEmail());
        mobile.setText(SharedPrefManager.getInstance(getContext()).getUser().getTeacherMobile());

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

        fetchDropdownDetailsViewModel.fetchDropdownDetails(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
    }

    private void dataObserver() {

        fetchDropdownDetailsViewModel = ViewModelProviders.of(this).get(FetchDropdownDetailsViewModel.class);
        LiveData<String> message = fetchDropdownDetailsViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "teacher_detail_found":
                        fetchList();
                        break;
                    case "Invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Invalid_teacherCode":
                        new SnackBar(relativeLayout, "Invalid Account");
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
        LiveData<ArrayList<TeacherClasses>> list = fetchDropdownDetailsViewModel.getList();

        list.observe(this, new Observer<ArrayList<TeacherClasses>>() {
            @Override
            public void onChanged(ArrayList<TeacherClasses> teacherClasses) {
                detailList.setLayoutManager(new LinearLayoutManager(getContext()));
                TeacherClassListAdapter teacherClassListAdapter = new TeacherClassListAdapter(getContext(), teacherClasses);
                detailList.setAdapter(teacherClassListAdapter);

                teacherClassListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (teacherClassListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}