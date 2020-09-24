package com.education.smartclass.roles.student.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.education.smartclass.models.StudentAssignmentSubmissionDetails;
import com.education.smartclass.roles.student.model.StudentAssignmentDetailViewModel;
import com.education.smartclass.roles.student.model.StudentFetchAssignmentListViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class StudentAssignmentSubmissionFragment extends Fragment {

    private TextView subject, time, title, description, download, remark, student_document, student_document_description, document_upload_btn, submitbtn;
    private ImageView status, delete;
    private EditText student_assignment_description_btn;
    private ProgressBar progressBar;

    private RelativeLayout relativeLayout;

    private StudentAssignmentSubmissionDetails studentAssignmentSubmissionDetail;

    private StudentAssignmentDetailViewModel studentAssignmentDetailViewModel;

    String getid, gettitle, getsubject, getdate, gettime, getdescription, getfile, getactive;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_assignment_submission, container, false);

        subject = view.findViewById(R.id.subject);
        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        download = view.findViewById(R.id.download_link);
        status = view.findViewById(R.id.status);
        remark = view.findViewById(R.id.remark);
        progressBar = view.findViewById(R.id.progress_bar);
        student_document = view.findViewById(R.id.student_document);
        student_document_description = view.findViewById(R.id.student_document_description);
        delete = view.findViewById(R.id.delete);
        document_upload_btn = view.findViewById(R.id.document_upload_btn);
        student_assignment_description_btn = view.findViewById(R.id.student_assignment_description_btn);
        submitbtn = view.findViewById(R.id.submitbtn);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        dataObserver();

        Bundle bundle = this.getArguments();

        getid = bundle.getString("assignmentId");
        gettitle = bundle.getString("title");
        getsubject = bundle.getString("subject");
        getdate = bundle.getString("date");
        gettime = bundle.getString("time");
        getdescription = bundle.getString("description");
        getfile = bundle.getString("file");
        getactive = bundle.getString("active");

        subject.setText(getsubject);
        title.setText(gettitle);
        time.setText(gettime + "(" + getdate + ")");
        if (getdescription.equals("")) {
            description.setVisibility(View.GONE);
        } else {
            description.setText(getdescription);
        }
        if (getactive.equals("true")) {
            status.setImageDrawable(getResources().getDrawable(R.drawable.ic_submit));
            progressBar.setVisibility(View.VISIBLE);
            studentAssignmentDetailViewModel.assignmentDetails(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                    getid, SharedPrefManager.getInstance(getContext()).getUser().getStudentId());
        } else {
            status.setImageDrawable(getResources().getDrawable(R.drawable.ic_missing));
            student_assignment_description_btn.setVisibility(View.VISIBLE);
            document_upload_btn.setVisibility(View.VISIBLE);
            submitbtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void dataObserver() {

        studentAssignmentDetailViewModel = ViewModelProviders.of(this).get(StudentAssignmentDetailViewModel.class);
        LiveData<String> message = studentAssignmentDetailViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        dataSetup();
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

    private void dataSetup() {

        LiveData<StudentAssignmentSubmissionDetails> data = studentAssignmentDetailViewModel.getData();

        data.observe(this, new Observer<StudentAssignmentSubmissionDetails>() {
            @Override
            public void onChanged(StudentAssignmentSubmissionDetails studentAssignmentSubmissionDetails) {

                studentAssignmentSubmissionDetail = studentAssignmentSubmissionDetails;

                student_document_description.setVisibility(View.VISIBLE);
                student_document.setVisibility(View.VISIBLE);
                student_document_description.setText(studentAssignmentSubmissionDetails.getStudentDescription());
                delete.setVisibility(View.VISIBLE);
                if (!studentAssignmentSubmissionDetails.equals("")) {
                    remark.setVisibility(View.VISIBLE);
                    remark.setText("Remark: " + studentAssignmentSubmissionDetails.getTeacherRemark());
                }
            }
        });
    }
}