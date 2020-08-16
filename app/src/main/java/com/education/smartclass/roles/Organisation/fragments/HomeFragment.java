package com.education.smartclass.roles.Organisation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.Adapter.HolderAdapter;
import com.education.smartclass.Adapter.TeacherAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherHolder;
import com.education.smartclass.models.Organisation;
import com.education.smartclass.roles.Organisation.model.HomeViewModel;
import com.education.smartclass.models.Teachers;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private RecyclerView teacher_list;
    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        teacher_list = view.findViewById(R.id.teacher_list);

        dataObserver();

        homeViewModel.fetchOrganisationList();

        return view;
    }

    private void dataObserver() {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        LiveData<String> message = homeViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "list_found":
                        fetchList();
                        break;
                    case "Invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Invalid_role":
                        new SnackBar(relativeLayout, "Invalid Account");
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

    private void fetchList() {
        LiveData<ArrayList<Teachers>> list = homeViewModel.getList();

        list.observe(this, new Observer<ArrayList<Teachers>>() {
            @Override
            public void onChanged(ArrayList<Teachers> teachers) {
                teacher_list.setLayoutManager(new LinearLayoutManager(getContext()));
                TeacherAdapter teacherAdapter = new TeacherAdapter(getContext(), teachers);
                teacher_list.setAdapter(teacherAdapter);
            }
        });
    }
}
