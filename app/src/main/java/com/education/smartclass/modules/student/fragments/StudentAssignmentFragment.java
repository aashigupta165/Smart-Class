package com.education.smartclass.modules.student.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.Adapter.StudentAssignmentListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.StudentAssignmentListHolder;
import com.education.smartclass.models.AssignmentDetailsList;
import com.education.smartclass.modules.student.model.StudentFetchAssignmentListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentAssignmentFragment extends Fragment {

    private TextView heading, no_data;
    private ImageView filter;
    private ProgressBar progressBar;
    private RecyclerView assignment_list;

    private StudentFetchAssignmentListViewModel fetchAssignmentListViewModel;

    private ArrayList<AssignmentDetailsList> assignmentDetailsListArrayList;

    private StudentAssignmentListAdapter assignmentListAdapter;

    private RelativeLayout relativeLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_questionaire, container, false);

        heading = view.findViewById(R.id.heading);
        heading.setText("Assignment");
        filter = view.findViewById(R.id.filter);
        no_data = view.findViewById(R.id.no_question);
        no_data.setText("No Assignment Available");
        assignment_list = view.findViewById(R.id.question_list);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        dataObserver();
        buttonClickEvents();

        fetchAssignmentListViewModel.fetchAssignmentList(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(), SharedPrefManager.getInstance(getContext()).getUser().getStudentSection(),
                SharedPrefManager.getInstance(getContext()).getUser().getStudentId());

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

    private void filterSelectionOptions(View v) {

        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();
        menu.findItem(R.id.coming).setVisible(false);
        menu.findItem(R.id.previous).setVisible(false);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.all:
                        assignmentListAdapter.getFilter().filter("all");
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
                assignmentListAdapter.getFilter().filter(selectedDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void dataObserver() {

        fetchAssignmentListViewModel = ViewModelProviders.of(this).get(StudentFetchAssignmentListViewModel.class);
        LiveData<String> message = fetchAssignmentListViewModel.getMessage();

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
    }

    private void fetchList() {

        LiveData<ArrayList<AssignmentDetailsList>> list = fetchAssignmentListViewModel.getList();

        list.observe(this, new Observer<ArrayList<AssignmentDetailsList>>() {
            @Override
            public void onChanged(ArrayList<AssignmentDetailsList> assignmentDetailsLists) {

                assignmentDetailsListArrayList = assignmentDetailsLists;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                assignment_list.setLayoutManager(linearLayoutManager);
                assignmentListAdapter = new StudentAssignmentListAdapter(getContext(), assignmentDetailsLists);
                assignment_list.setAdapter(assignmentListAdapter);

                if (assignmentListAdapter.getItemCount() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                }

                assignmentListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (assignmentListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                assignmentListAdapter.setOnItemClickListener(new StudentAssignmentListHolder.OnItemClickListener() {
                    @Override
                    public void onCardClick(View view, int position) {
                        openAssignmentDetails(position);
                    }

                    @Override
                    public void onDrag(View view, int position) {

                    }
                });
            }
        });
    }

    private void openAssignmentDetails(int position) {

        Bundle bundle = new Bundle();

        bundle.putString("assignmentId", assignmentDetailsListArrayList.get(position).getAssignmentId());
        bundle.putString("title", assignmentDetailsListArrayList.get(position).getAssignmentTitle());
        bundle.putString("subject", assignmentDetailsListArrayList.get(position).getSubjectAssignment());
        bundle.putString("date", assignmentDetailsListArrayList.get(position).getAssignmentDate());
        bundle.putString("time", assignmentDetailsListArrayList.get(position).getAssignmentTime());
        bundle.putString("description", assignmentDetailsListArrayList.get(position).getDescription());
        bundle.putString("file", assignmentDetailsListArrayList.get(position).getFile());
        bundle.putString("active", assignmentDetailsListArrayList.get(position).getActive());

        StudentAssignmentSubmissionFragment fragment = new StudentAssignmentSubmissionFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}
