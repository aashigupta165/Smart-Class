package com.education.smartclass.roles.student.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.StudentScheduleListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.models.ReadStudentScheduleDetails;
import com.education.smartclass.roles.student.model.ReadSchedulesViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentScheduleFragment extends Fragment {

    private ImageView filter;
    private TextView no_data;
    private RelativeLayout relativeLayout;
    private RecyclerView schedule_list;
    private ReadSchedulesViewModel readSchedulesViewModel;

    private StudentScheduleListAdapter studentScheduleListAdapter;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false);

        filter = view.findViewById(R.id.filter);
        no_data = view.findViewById(R.id.no_class);
        schedule_list = view.findViewById(R.id.schedule_list);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        dataObserver();
        buttonClickEvents();

        readSchedulesViewModel.fetchScheduleList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo());

        return view;
    }

    private void buttonClickEvents() {

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterSelectionOptions(v);
            }
        });
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
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });
    }

    private void fetchList() {

        LiveData<ArrayList<ReadStudentScheduleDetails>> list = readSchedulesViewModel.getList();

        list.observe(this, new Observer<ArrayList<ReadStudentScheduleDetails>>() {
            @Override
            public void onChanged(ArrayList<ReadStudentScheduleDetails> readStudentScheduleDetails) {

                schedule_list.setLayoutManager(new LinearLayoutManager(getContext()));
                studentScheduleListAdapter = new StudentScheduleListAdapter(getContext(), readStudentScheduleDetails);
                schedule_list.setAdapter(studentScheduleListAdapter);

                if (studentScheduleListAdapter.getItemCount() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                }

                studentScheduleListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (studentScheduleListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void filterSelectionOptions(View v) {

        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.all:
                        studentScheduleListAdapter.getFilter().filter("all");
                        return true;
                    case R.id.coming:
                        studentScheduleListAdapter.getFilter().filter("filter1");
                        return true;
                    case R.id.previous:
                        studentScheduleListAdapter.getFilter().filter("filter2");
                        return true;
                    case R.id.by_date:
                        selectDate();
                        return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void selectDate() {

        DatePickerDialog.OnDateSetListener setListener;
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String selectedDate = dayOfMonth + "-" + month + "-" + year;
                studentScheduleListAdapter.getFilter().filter(selectedDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}