package com.education.smartclass.roles.teacher.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.education.smartclass.Adapter.TeacherFetchStudentAssignmentListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.TeacherFetchStudentAssignmentListHolder;
import com.education.smartclass.models.TeacherFetchStudentAssignmentListDetails;
import com.education.smartclass.roles.teacher.model.StudentAssignmentListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.File;
import java.util.ArrayList;

public class StudentAssignmentListFragment extends Fragment {

    private TextView time, title, download_all;

    private RelativeLayout relativeLayout;

    private RecyclerView assignment_list;

    private ArrayList<TeacherFetchStudentAssignmentListDetails> teacherFetchStudentAssignmentListDetailsArrayList;

    private TeacherFetchStudentAssignmentListAdapter teacherFetchStudentAssignmentListAdapter;

    private StudentAssignmentListViewModel studentAssignmentListViewModel;

    private ProgressBar progressBar;

    private String getid, gettitle, getdate, gettime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_assignment_list, container, false);

        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);
        download_all = view.findViewById(R.id.download_all);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        assignment_list = view.findViewById(R.id.assignment_list);

        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = this.getArguments();

        getid = bundle.getString("assignmentId");
        gettitle = bundle.getString("assignmentTitle");
        gettime = bundle.getString("assignmentTime");
        getdate = bundle.getString("assignmentDate");

        time.setText("Uploaded at: " + gettime + "(" + getdate + ")");
        title.setText(gettitle);

        dataObserver();
        buttonClickEvents();

        studentAssignmentListViewModel.fetchAssignments(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), getid);

        return view;
    }

    private void buttonClickEvents() {

        download_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teacherFetchStudentAssignmentListDetailsArrayList.isEmpty()) {
                    new SnackBar(relativeLayout, "Empty List");
                } else {
                    for (int i = 0; i < teacherFetchStudentAssignmentListDetailsArrayList.size(); i++) {
                        if (teacherFetchStudentAssignmentListDetailsArrayList.get(i).getResponseActive().equals("true")) {
                            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(teacherFetchStudentAssignmentListDetailsArrayList.get(i).getStudentFile());
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
                }
            }
        });
    }

    private void dataObserver() {

        studentAssignmentListViewModel = ViewModelProviders.of(this).get(StudentAssignmentListViewModel.class);
        LiveData<String> message = studentAssignmentListViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        assignmentListSetUp();
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
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void assignmentListSetUp() {

        LiveData<ArrayList<TeacherFetchStudentAssignmentListDetails>> list = studentAssignmentListViewModel.getList();

        list.observe(this, new Observer<ArrayList<TeacherFetchStudentAssignmentListDetails>>() {
            @Override
            public void onChanged(ArrayList<TeacherFetchStudentAssignmentListDetails> assignmentListDetails) {

                teacherFetchStudentAssignmentListDetailsArrayList = assignmentListDetails;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                assignment_list.setLayoutManager(linearLayoutManager);
                teacherFetchStudentAssignmentListAdapter = new TeacherFetchStudentAssignmentListAdapter(getContext(), assignmentListDetails);
                assignment_list.setAdapter(teacherFetchStudentAssignmentListAdapter);

                teacherFetchStudentAssignmentListAdapter.setOnItemClickListener(new TeacherFetchStudentAssignmentListHolder.OnItemClickListener() {
                    @Override
                    public void onDownload(View view, int position) {
                        download(position);
                    }

                    @Override
                    public void addRemark(View view, int position) {
                        postRemark(position);
                    }
                });
            }
        });
    }

    private void postRemark(int position) {

    }

    private void download(int position) {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(teacherFetchStudentAssignmentListDetailsArrayList.get(position).getStudentFile());
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
