package com.education.smartclass.modules.teacher.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.education.smartclass.R;
import com.education.smartclass.modules.Organisation.fragments.OrganisationScheduleFragment;
import com.education.smartclass.modules.teacher.model.ScheduleUpdateViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.util.Calendar;

public class ScheduleUpdateFragment extends Fragment {

    private TextView date, time, updatebtn;

    private String bundleId, bundleDate, bundleTime;

    private RelativeLayout relativeLayout;

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    private final int minute = calendar.get(Calendar.MINUTE);

    private ScheduleUpdateViewModel scheduleUpdateViewModel;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_update, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        updatebtn = view.findViewById(R.id.updatebtn);

        progressDialog = new ProgressDialog(getContext());

        Bundle bundle = this.getArguments();

        bundleId = bundle.getString("id");
        bundleDate = bundle.getString("date");
        bundleTime = bundle.getString("time");

        date.setText(bundleDate);
        time.setText(bundleTime);

        dataObserver();
        buttonClickEvents();

        return view;
    }

    private void buttonClickEvents() {

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String selectedDate = dayOfMonth + "-" + month + "-" + year;
                date.setText(selectedDate);
            }
        };

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void dataObserver() {

        scheduleUpdateViewModel = ViewModelProviders.of(this).get(ScheduleUpdateViewModel.class);
        LiveData<String> message = scheduleUpdateViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();

                switch (s) {
                    case "schedule_updated":
                        new SnackBar(relativeLayout, "Schedule Updated");
                        if (SharedPrefManager.getInstance(getContext()).getUser().getRole().equals("Organisation")) {
                            OrganisationScheduleFragment fragment = new OrganisationScheduleFragment();
                            getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        } else {
                            TeacherScheduleFragment fragment = new TeacherScheduleFragment();
                            getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        }
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    case "Session Expire":
                        new SnackBar(relativeLayout, "Session Expire, Please Login Again!");
                        new SessionExpire(getContext());
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void update() {

        if (date.getText().toString().equals(bundleDate) && time.getText().toString().equals(bundleTime)) {
            new SnackBar(relativeLayout, "Change Date or Time before Updation!");
            return;
        }

        progressDialog.setMessage("Updating");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();

        if (SharedPrefManager.getInstance(getContext()).getUser().getRole().equals("Teacher")) {
            scheduleUpdateViewModel.update(orgCode, teacherCode, bundleId, date.getText().toString(), time.getText().toString());
        } else {
            scheduleUpdateViewModel.update(orgCode, "Organisation", bundleId, date.getText().toString(), time.getText().toString());
        }
    }
}