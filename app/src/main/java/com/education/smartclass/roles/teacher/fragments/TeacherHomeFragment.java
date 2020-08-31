package com.education.smartclass.roles.teacher.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.TeacherScheduleListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.ScheduleListHolder;
import com.education.smartclass.models.ReadTeacherScheduleDetails;
import com.education.smartclass.roles.teacher.model.ReadSchedulesViewModel;
import com.education.smartclass.roles.teacher.model.ScheduleDeleteViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

public class TeacherHomeFragment extends Fragment {

    private ImageView filter;
    private TextView filterheading, no_data;

    private RelativeLayout relativeLayout;
    private RecyclerView schedule_list;
    private ReadSchedulesViewModel readSchedulesViewModel;

    private ArrayList<ReadTeacherScheduleDetails> readTeacherScheduleDetailsArrayList;

    private TeacherScheduleListAdapter teacherScheduleListAdapter;

    private ScheduleDeleteViewModel scheduleDeleteViewModel;

    private ProgressDialog progressDialog;

    private ProgressBar progressBar;

    private int positionDelete;

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

        readSchedulesViewModel.fetchScheduleList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());

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
                    default:
                        new SnackBar(relativeLayout, "Invalid Credentials");
                }
            }
        });

        scheduleDeleteViewModel = ViewModelProviders.of(this).get(ScheduleDeleteViewModel.class);
        LiveData<String> msg = scheduleDeleteViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "schedule_deleted":
                        readTeacherScheduleDetailsArrayList.remove(positionDelete);
                        teacherScheduleListAdapter.notifyItemRemoved(positionDelete);
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
        LiveData<ArrayList<ReadTeacherScheduleDetails>> list = readSchedulesViewModel.getList();

        list.observe(this, new Observer<ArrayList<ReadTeacherScheduleDetails>>() {
            @Override
            public void onChanged(ArrayList<ReadTeacherScheduleDetails> readTeacherScheduleDetails) {

                readTeacherScheduleDetailsArrayList = readTeacherScheduleDetails;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                schedule_list.setLayoutManager(linearLayoutManager);
                teacherScheduleListAdapter = new TeacherScheduleListAdapter(getContext(), readTeacherScheduleDetails);
                teacherScheduleListAdapter.getFilter().filter("filter1");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        schedule_list.setAdapter(teacherScheduleListAdapter);
                    }
                }, 10);

                teacherScheduleListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (teacherScheduleListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                teacherScheduleListAdapter.setOnItemClickListener(new ScheduleListHolder.OnItemClickListener() {
                    @Override
                    public void onCardClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(View view, int position) {
                        showmenu(view, position);
                    }

                    @Override
                    public void onDrag(View view, int position) {
                    }
                });
            }
        });
    }

    private void showmenu(View view, int position) {

        TextView optionMenu = view.findViewById(R.id.optionMenu);

        optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionMenu(v, position);
            }
        });
    }

    private void openOptionMenu(View v, int position) {

        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        edit(position);
                        break;
                    case R.id.delete:
                        delete(position);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void edit(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("id", readTeacherScheduleDetailsArrayList.get(position).getScheduleId());
        bundle.putString("date", readTeacherScheduleDetailsArrayList.get(position).getScheduleDate());
        bundle.putString("time", readTeacherScheduleDetailsArrayList.get(position).getScheduleTime());

        ScheduleUpdateFragment fragment = new ScheduleUpdateFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }

    private void delete(int position) {

        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
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

    private void deleteItem(int position) {

        progressDialog.setMessage("Deleting");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();
        String teacherCode = SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode();

        positionDelete = position;

        scheduleDeleteViewModel.delete(orgCode, teacherCode, readTeacherScheduleDetailsArrayList.get(position).getScheduleId());
    }
}