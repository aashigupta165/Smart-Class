package com.education.smartclass.roles.Organisation.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.Adapter.TeacherListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.ReplyListHolder;
import com.education.smartclass.holder.TeacherListHolder;
import com.education.smartclass.roles.Organisation.model.HomeViewModel;
import com.education.smartclass.models.Teachers;
import com.education.smartclass.roles.Organisation.model.StateChangeViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class OrganisationHomeFragment extends Fragment {

    private TextView no_data;
    private RelativeLayout relativeLayout;
    private RecyclerView teacher_list;
    private HomeViewModel homeViewModel;

    private ArrayList<Teachers> teachersArrayList;

    private ProgressBar progressBar;

    private ProgressDialog progressDialog;

    private int positionOfTeacher;

    private StateChangeViewModel stateChangeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);
        teacher_list = view.findViewById(R.id.teacher_list);
        no_data = view.findViewById(R.id.no_data);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        progressDialog = new ProgressDialog(getContext());

        dataObserver();

        homeViewModel.fetchOrganisationList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode());

        return view;
    }

    private void dataObserver() {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        LiveData<String> message = homeViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
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
                        new SnackBar(relativeLayout, "Please Try Again Later!1cb");
                }
            }
        });

        stateChangeViewModel = ViewModelProviders.of(this).get(StateChangeViewModel.class);
        LiveData<String> msg = stateChangeViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "state_changed":
                        homeViewModel.fetchOrganisationList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode());
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

                teachersArrayList = teachers;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                teacher_list.setLayoutManager(linearLayoutManager);
                TeacherListAdapter teacherListAdapter = new TeacherListAdapter(getContext(), teachers);
                teacher_list.setAdapter(teacherListAdapter);

                teacherListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (teacherListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                teacherListAdapter.setOnItemClickListener(new TeacherListHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        positionOfTeacher = position;
                        statusChange();
                    }
                });
            }
        });
    }

    private void statusChange() {

        String msg = "";

        if (teachersArrayList.get(positionOfTeacher).getActive().equals("true")) {
            msg = "Deactivate " + teachersArrayList.get(positionOfTeacher).getTeacherName();
        } else {
            msg = "Activate " + teachersArrayList.get(positionOfTeacher).getTeacherName();
        }

        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to " + msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toggle();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void toggle() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = teachersArrayList.get(positionOfTeacher).getTeacherCode();

        stateChangeViewModel.stateChange(orgCode, teacherCode);
    }
}
