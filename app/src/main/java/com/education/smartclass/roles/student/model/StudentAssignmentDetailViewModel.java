package com.education.smartclass.roles.student.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.StudentAssignmentSubmissionDetails;
import com.education.smartclass.response.StudentAssignmentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAssignmentDetailViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<StudentAssignmentSubmissionDetails> details = new MutableLiveData<>();

    public void assignmentDetails(String orgCode, String assignmentId, String studentId) {

        Call<StudentAssignmentResponse> call = RetrofitClient.getInstance().getApi().assignmentDetails(orgCode, assignmentId, "Student", studentId);
        call.enqueue(new Callback<StudentAssignmentResponse>() {
            @Override
            public void onResponse(Call<StudentAssignmentResponse> call, Response<StudentAssignmentResponse> response) {
                if (response.isSuccessful()) {
                    StudentAssignmentResponse studentAssignmentResponse = response.body();
                    message.setValue(studentAssignmentResponse.getMessage());
                    details.setValue(studentAssignmentResponse.getData());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<StudentAssignmentResponse> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<StudentAssignmentSubmissionDetails> getData() {
        return details;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
