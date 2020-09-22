package com.education.smartclass.roles.teacher.fragments;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.Adapter.TeacherAssignmentListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherAssignmentListHolder;
import com.education.smartclass.models.TeacherAssignmentDetailsList;
import com.education.smartclass.roles.teacher.model.DeleteAssignmentViewModel;
import com.education.smartclass.roles.teacher.model.TeacherFetchAssignmentListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class TeacherAssignmentFragment extends Fragment {

    private TextView heading, no_data;
    private ImageView filter;
    private ProgressBar progressBar;
    private RecyclerView assignment_list;

    private TeacherFetchAssignmentListViewModel fetchAssignmentListViewModel;
    private DeleteAssignmentViewModel deleteAssignmentViewModel;

    private ArrayList<TeacherAssignmentDetailsList> teacherAssignmentDetailsListArrayList;

    private TeacherAssignmentListAdapter assignmentListAdapter;

    private ProgressDialog progressDialog;

    private int positionDelete;

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

        progressDialog = new ProgressDialog(getContext());

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        dataObserver();
        buttonClickEvents();

        fetchAssignmentListViewModel.fetchAssignmentLists(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());

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

        fetchAssignmentListViewModel = ViewModelProviders.of(this).get(TeacherFetchAssignmentListViewModel.class);
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

        deleteAssignmentViewModel = ViewModelProviders.of(this).get(DeleteAssignmentViewModel.class);
        LiveData<String> msgs = deleteAssignmentViewModel.getMessage();

        msgs.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "assignment_deleted":
                        teacherAssignmentDetailsListArrayList.remove(positionDelete);
                        assignmentListAdapter.notifyItemRemoved(positionDelete);
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

    private void fetchList() {

        LiveData<ArrayList<TeacherAssignmentDetailsList>> list = fetchAssignmentListViewModel.getList();

        list.observe(this, new Observer<ArrayList<TeacherAssignmentDetailsList>>() {
            @Override
            public void onChanged(ArrayList<TeacherAssignmentDetailsList> teacherAssignmentDetailsLists) {

                teacherAssignmentDetailsListArrayList = teacherAssignmentDetailsLists;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                assignment_list.setLayoutManager(linearLayoutManager);
                assignmentListAdapter = new TeacherAssignmentListAdapter(getContext(), teacherAssignmentDetailsLists);
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

                assignmentListAdapter.setOnItemClickListener(new TeacherAssignmentListHolder.OnItemClickListener() {
                    @Override
                    public void onCardClick(View view, int position) {
                        openAssignmentDetails(position);
                    }

                    @Override
                    public void onDownload(View view, int position) {
                        download(position);
                    }

                    @Override
                    public void onDelete(View view, int position) {
                        delete(position);
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

//        bundle.putString("questionId", questionArrayList.get(position).getQuestionId());
//        bundle.putString("question", questionArrayList.get(position).getQuestion());
//        bundle.putString("questionType", questionArrayList.get(position).getPurposeOfQuestion());
//        bundle.putString("askerName", questionArrayList.get(position).getQuestionAskerName());
//        bundle.putString("askerRole", questionArrayList.get(position).getQuestionAskerRole());
//        bundle.putString("askerClass", questionArrayList.get(position).getQuestionForClass());
//        bundle.putString("askerSection", questionArrayList.get(position).getQuestionForSection());
//        bundle.putString("date", questionArrayList.get(position).getQuestionDateTime());

        TeacherQuestionRepliesFragment fragment = new TeacherQuestionRepliesFragment();
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

        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        positionDelete = position;

        deleteAssignmentViewModel.delete(teacherAssignmentDetailsListArrayList.get(position).getAssignmentId(), SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode());
    }

    private void download(int position) {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(teacherAssignmentDetailsListArrayList.get(position).getFile());
        File file = new File(uri.getPath());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(file.getName());
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.getName());
        downloadManager.enqueue(request);
    }
}
