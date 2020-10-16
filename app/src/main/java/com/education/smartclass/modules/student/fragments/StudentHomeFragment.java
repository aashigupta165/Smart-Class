package com.education.smartclass.modules.student.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.StudentScheduleListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.ScheduleListHolder;
import com.education.smartclass.models.ReadStudentScheduleDetails;
import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.modules.student.model.ReadSchedulesViewModel;
import com.education.smartclass.modules.teacher.model.ScheduleStudentsViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class StudentHomeFragment extends Fragment {

    private ImageView filter;
    private TextView filterheading, no_data;
    private RelativeLayout relativeLayout;
    private RecyclerView schedule_list;
    private ReadSchedulesViewModel readSchedulesViewModel;

    private ArrayList<StudentDetail> studentDetailArrayList;
    private ArrayList<ReadStudentScheduleDetails> readStudentScheduleDetailsArrayList;

    private StudentScheduleListAdapter studentScheduleListAdapter;
    private ScheduleStudentsViewModel scheduleStudentsViewModel;

    private ProgressBar progressBar;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false);

        filter = view.findViewById(R.id.filter);
        filter.setVisibility(View.GONE);
        filterheading = view.findViewById(R.id.filter_heading);
        filterheading.setVisibility(View.GONE);

        no_data = view.findViewById(R.id.no_class);
        schedule_list = view.findViewById(R.id.schedule_list);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        progressDialog = new ProgressDialog(getContext());

        dataObserver();
        fetchStudentsList();

        readSchedulesViewModel.fetchScheduleList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentId());

        return view;
    }

    private void dataObserver() {

        readSchedulesViewModel = ViewModelProviders.of(this).get(ReadSchedulesViewModel.class);
        LiveData<String> message = readSchedulesViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        fetchList();
                        break;
                    case "invalid_orgCode":
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

        scheduleStudentsViewModel = ViewModelProviders.of(this).get(ScheduleStudentsViewModel.class);
        LiveData<String> msgs = scheduleStudentsViewModel.getMessage();

        msgs.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "all_students_selected":
                    case "list_found":
                        break;
                    case "Internet_Issue":
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        progressDialog.dismiss();
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void fetchList() {

        LiveData<ArrayList<ReadStudentScheduleDetails>> list = readSchedulesViewModel.getList();

        list.observe(this, new Observer<ArrayList<ReadStudentScheduleDetails>>() {
            @Override
            public void onChanged(ArrayList<ReadStudentScheduleDetails> readStudentScheduleDetails) {

                readStudentScheduleDetailsArrayList = readStudentScheduleDetails;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                schedule_list.setLayoutManager(linearLayoutManager);
                studentScheduleListAdapter = new StudentScheduleListAdapter(getContext(), readStudentScheduleDetails);
                studentScheduleListAdapter.getFilter().filter("home_filter");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        schedule_list.setAdapter(studentScheduleListAdapter);
                    }
                }, 10);

                studentScheduleListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (studentScheduleListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                studentScheduleListAdapter.setOnItemClickListener(new ScheduleListHolder.OnItemClickListener() {
                    @Override
                    public void onCardClick(View view, int position) {
                        showSelectedStudents(position);
                    }

                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onDrag(View view, int position) {
                    }
                });
            }
        });
    }

    private void showSelectedStudents(int position) {

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        scheduleStudentsViewModel.fetchScheduleList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), readStudentScheduleDetailsArrayList.get(position).getScheduleId());
    }

    private void fetchStudentsList() {

        LiveData<ArrayList<StudentDetail>> list = scheduleStudentsViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<StudentDetail>>() {
            @Override
            public void onChanged(ArrayList<StudentDetail> studentDetails) {

                studentDetailArrayList = studentDetails;
                showStudentsDialog();
            }
        });
    }

    private void showStudentsDialog() {

        if (studentDetailArrayList == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("All students have to attend the meeting.");
            builder.setCancelable(true);
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Selected Students");

            ArrayList<String> name = new ArrayList<>();
            int i = 0;
            for (StudentDetail s : studentDetailArrayList) {
                name.add(i, s.getStudentName() + " (" + s.getStudentRollNo() + ")");
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, name);

            builder.setAdapter(adapter, null);
            builder.setCancelable(true);
            builder.show();
        }
    }
}