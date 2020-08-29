package com.education.smartclass.roles.Organisation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.StudentListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.roles.Organisation.model.StudentListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class StudentListFragment extends Fragment {

    private TextView no_data;
    private RelativeLayout relativeLayout;
    private RecyclerView student_list;
    private StudentListViewModel studentListViewModel;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        student_list = view.findViewById(R.id.student_list);
        no_data = view.findViewById(R.id.no_data);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        dataObserver();

        studentListViewModel.fetchStudentList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Student");

        return view;
    }

    private void dataObserver() {

        studentListViewModel = ViewModelProviders.of(this).get(StudentListViewModel.class);
        LiveData<String> message = studentListViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        fetchList();
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

    private void fetchList() {

        LiveData<ArrayList<StudentDetail>> list = studentListViewModel.getList();

        list.observe(this, new Observer<ArrayList<StudentDetail>>() {
            @Override
            public void onChanged(ArrayList<StudentDetail> students) {

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                student_list.setLayoutManager(linearLayoutManager);
                StudentListAdapter studentListAdapter = new StudentListAdapter(getContext(), students);
                student_list.setAdapter(studentListAdapter);

                if (studentListAdapter.getItemCount() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}