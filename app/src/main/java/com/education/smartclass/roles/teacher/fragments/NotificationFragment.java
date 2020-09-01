package com.education.smartclass.roles.teacher.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.NotificationsListAdapter;
import com.education.smartclass.Adapter.TeacherScheduleListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.NotificationListHolder;
import com.education.smartclass.roles.student.model.StudentNotificationViewModel;
import com.education.smartclass.roles.teacher.model.TeacherNotificationViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private TextView no_data;
    private RecyclerView notificationList;

    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;

    private NotificationsListAdapter notificationsListAdapter;

    private StudentNotificationViewModel studentNotificationViewModel;
    private TeacherNotificationViewModel teacherNotificationViewModel;

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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        no_data = view.findViewById(R.id.no_data);
        notificationList = view.findViewById(R.id.notifications);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        if (SharedPrefManager.getInstance(getContext()).getUser().getRole().equals("Organisation")) {
            teacherDataObserver();
            teacherNotificationViewModel.fetchNotifications(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Organisation", "");
        } else if (SharedPrefManager.getInstance(getContext()).getUser().getRole().equals("Teacher")) {
            teacherDataObserver();
            teacherNotificationViewModel.fetchNotifications(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), "Teacher",
                    SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
        } else {
            studentDataObserver();
            studentNotificationViewModel.fetchNotifications(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                    SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection(),
                    SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo());
        }

        return view;
    }

    private void teacherDataObserver() {

        teacherNotificationViewModel = ViewModelProviders.of(this).get(TeacherNotificationViewModel.class);
        LiveData<String> message = teacherNotificationViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        setTeacherList();
                        break;
                    case "no_data":
                        no_data.setVisibility(View.VISIBLE);
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please try again later!");
                }
            }
        });
    }

    private void studentDataObserver() {

        studentNotificationViewModel = ViewModelProviders.of(this).get(StudentNotificationViewModel.class);
        LiveData<String> message = studentNotificationViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        setStudentList();
                        break;
                    case "no_data":
                        no_data.setVisibility(View.VISIBLE);
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please try again later!");
                }
            }
        });
    }

    private void setTeacherList() {

        LiveData<ArrayList<String>> list = teacherNotificationViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> notifications) {

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                notificationList.setLayoutManager(linearLayoutManager);
                notificationsListAdapter = new NotificationsListAdapter(getContext(), notifications);
                notificationList.setAdapter(notificationsListAdapter);
            }
        });
    }

    private void setStudentList() {

        LiveData<ArrayList<String>> list = studentNotificationViewModel.getList();

        list.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> notifications) {

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                notificationList.setLayoutManager(linearLayoutManager);
                notificationsListAdapter = new NotificationsListAdapter(getContext(), notifications);
                notificationList.setAdapter(notificationsListAdapter);
            }
        });
    }
}