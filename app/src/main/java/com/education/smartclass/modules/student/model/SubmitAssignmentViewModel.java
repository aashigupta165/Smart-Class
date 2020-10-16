package com.education.smartclass.modules.student.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitAssignmentViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void submitAssignment(String orgCode, String assignmentId, String studentId, String studentDescription, String assignmentType, MultipartBody.Part file) {

        String assignmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String assignmentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        RequestBody OrgCode = RequestBody.create(orgCode, MediaType.parse("text/plain"));
        RequestBody AssignmentId = RequestBody.create(assignmentId, MediaType.parse("text/plain"));
        RequestBody StudentId = RequestBody.create(studentId, MediaType.parse("text/plain"));
        RequestBody StudentDescription = RequestBody.create(studentDescription, MediaType.parse("text/plain"));
        RequestBody AssignmentType = RequestBody.create(assignmentType, MediaType.parse("text/plain"));
        RequestBody AssignmentDate = RequestBody.create(assignmentDate, MediaType.parse("text/plain"));
        RequestBody AssignmentTime = RequestBody.create(assignmentTime, MediaType.parse("text/plain"));
        RequestBody Role = RequestBody.create("Assignment", MediaType.parse("text/plain"));

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().submitAssignment(OrgCode, AssignmentId, StudentId, StudentDescription,
                AssignmentDate, AssignmentTime, Role, AssignmentType, file);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    message.setValue(messageResponse.getMessage());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
