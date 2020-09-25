package com.education.smartclass.roles.student.fragments;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.education.smartclass.R;
import com.education.smartclass.models.StudentAssignmentSubmissionDetails;
import com.education.smartclass.roles.student.model.DeleteAssignmentViewModel;
import com.education.smartclass.roles.student.model.StudentAssignmentDetailViewModel;
import com.education.smartclass.roles.student.model.SubmitAssignmentViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SessionExpire;
import com.education.smartclass.utils.SnackBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class StudentAssignmentSubmissionFragment extends Fragment {

    private TextView subject, time, title, description, download, remark, student_document_time, student_document, student_document_description,
            document_upload_btn, submitbtn;
    private ImageView status, delete;
    private EditText student_assignment_description_btn;
    private ProgressBar progressBar;

    private RelativeLayout relativeLayout;

    private ProgressDialog progressDialog;

    private StudentAssignmentSubmissionDetails studentAssignmentSubmissionDetail;

    private StudentAssignmentDetailViewModel studentAssignmentDetailViewModel;
    private SubmitAssignmentViewModel submitAssignmentViewModel;
    private DeleteAssignmentViewModel deleteAssignmentViewModel;

    private MultipartBody.Part file;
    private String type;

    String getid, gettitle, getsubject, getdate, gettime, getdescription, getfile, getactive;

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
        student_document_time = view.findViewById(R.id.student_document_time);
        student_document = view.findViewById(R.id.student_document);
        student_document_description = view.findViewById(R.id.student_document_description);
        delete = view.findViewById(R.id.delete);
        document_upload_btn = view.findViewById(R.id.document_upload_btn);
        student_assignment_description_btn = view.findViewById(R.id.student_assignment_description_btn);
        submitbtn = view.findViewById(R.id.submitbtn);
        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        dataObserver();
        buttonClickEvents();

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

    private void buttonClickEvents() {

        document_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAssignment();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAssignment();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAssignment(getfile);
            }
        });

        student_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAssignment(studentAssignmentSubmissionDetail.getStudentFile());
            }
        });
    }

    private void downloadAssignment(String fileName) {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(fileName);
        File file = new File(uri.getPath());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(file.getName());
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.getName());
        downloadManager.enqueue(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                ContentResolver contentResolver = getContext().getContentResolver();
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                String extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
                Date date = Calendar.getInstance().getTime();
                String name = SharedPrefManager.getInstance(getContext()).getUser().getStudentName() + "_" +
                        SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo() + "_" + date + " Assignment.";
                byte[] byteArray = null;
                if (extension.toLowerCase().equals("pdf")) {
                    type = "pdf";
                    InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    byteArray = new byte[inputStream.available()];
                    inputStream.read(byteArray);
                } else if (extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("png")) {
                    type = "image";
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(getActivity().getContentResolver().openFileDescriptor(data.getData(), "r").getFileDescriptor());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                } else {
                    new SnackBar(relativeLayout, "Please Select Image or PDF File!");
                    return;
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), byteArray);
                file = MultipartBody.Part.createFormData("file", name + extension, requestBody);
                File path = new File(uri.getPath());
                document_upload_btn.setText(path.getName());
                new SnackBar(relativeLayout, "File Uploaded Successfully");
            } else {
                new SnackBar(relativeLayout, "You haven't picked File.");
            }
        } catch (Exception e) {
            new SnackBar(relativeLayout, "Something went wrong");
        }
    }

    private void dataObserver() {

        studentAssignmentDetailViewModel = ViewModelProviders.of(this).get(StudentAssignmentDetailViewModel.class);
        LiveData<String> message = studentAssignmentDetailViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
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

        submitAssignmentViewModel = ViewModelProviders.of(this).get(SubmitAssignmentViewModel.class);
        LiveData<String> msg = submitAssignmentViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "assignment_response_saved":
                        document_upload_btn.setVisibility(View.GONE);
                        student_assignment_description_btn.setVisibility(View.GONE);
                        submitbtn.setVisibility(View.GONE);
                        status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_submit));
                        studentAssignmentDetailViewModel.assignmentDetails(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                                getid, SharedPrefManager.getInstance(getContext()).getUser().getStudentId());
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
                    case "assignment_response_deleted":
                        document_upload_btn.setVisibility(View.VISIBLE);
                        student_assignment_description_btn.setVisibility(View.VISIBLE);
                        submitbtn.setVisibility(View.VISIBLE);
                        status.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_missing));
                        student_document.setVisibility(View.GONE);
                        student_document_description.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        remark.setVisibility(View.GONE);
                        time.setVisibility(View.GONE);
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

                student_document_time.setVisibility(View.VISIBLE);
                student_document_time.setText("Uploaded at: " + studentAssignmentSubmissionDetails.getSubmitTime() + "(" + studentAssignmentSubmissionDetails.getSubmitDate()
                        + ")");
                student_document.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                if (!studentAssignmentSubmissionDetails.getStudentDescription().equals("")) {
                    student_document_description.setVisibility(View.VISIBLE);
                    student_document_description.setText(studentAssignmentSubmissionDetails.getStudentDescription());
                } else {
                    student_document_description.setVisibility(View.GONE);
                }
                if (!studentAssignmentSubmissionDetails.getTeacherRemark().equals("")) {
                    remark.setVisibility(View.VISIBLE);
                    remark.setText("Remark: " + studentAssignmentSubmissionDetails.getTeacherRemark());
                } else {
                    remark.setVisibility(View.GONE);
                }
            }
        });
    }

    private void submitAssignment() {

        if (document_upload_btn.getText().toString().equals("")) {
            new SnackBar(relativeLayout, "Please upload the document!");
            return;
        }

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        submitAssignmentViewModel.submitAssignment(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(),
                getid, SharedPrefManager.getInstance(getContext()).getUser().getStudentId(), student_assignment_description_btn.getText().toString(),
                type, file);
    }

    private void deleteAssignment() {

        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        deleteAssignmentViewModel.deleteAssignment(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), getid,
                SharedPrefManager.getInstance(getContext()).getUser().getStudentId());
    }
}