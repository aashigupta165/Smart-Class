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

import com.education.smartclass.Adapter.ClassListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.roles.Organisation.model.ClassListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class ClassListFragment extends Fragment {

    private TextView no_data;
    private RelativeLayout relativeLayout;
    private RecyclerView class_list;
    private ClassListViewModel classListViewModel;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        class_list = view.findViewById(R.id.class_list);
        no_data = view.findViewById(R.id.no_data);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        dataObserver();

        classListViewModel.fetchStudentList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Class");

        return view;
    }

    private void dataObserver() {

        classListViewModel = ViewModelProviders.of(this).get(ClassListViewModel.class);
        LiveData<String> message = classListViewModel.getMessage();

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

        LiveData<ArrayList<OrgClassList>> list = classListViewModel.getList();

        list.observe(this, new Observer<ArrayList<OrgClassList>>() {
            @Override
            public void onChanged(ArrayList<OrgClassList> classLists) {

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                class_list.setLayoutManager(linearLayoutManager);
                ClassListAdapter classListAdapter = new ClassListAdapter(getContext(), classLists);
                class_list.setAdapter(classListAdapter);

                if (classListAdapter.getItemCount() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}