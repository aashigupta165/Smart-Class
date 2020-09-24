package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.TeacherFetchStudentAssignmentListDetails;
import com.education.smartclass.response.TeacherFetchStudentAssignmentListResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAssignmentListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TeacherFetchStudentAssignmentListDetails>> list = new MutableLiveData<>();

    public void fetchAssignments(String orgCode, String assignmentId) {

        Call<TeacherFetchStudentAssignmentListResponse> call = RetrofitClient.getInstance().getApi().studentAssignmentList(orgCode, assignmentId, "Teacher");
        call.enqueue(new Callback<TeacherFetchStudentAssignmentListResponse>() {
            @Override
            public void onResponse(Call<TeacherFetchStudentAssignmentListResponse> call, Response<TeacherFetchStudentAssignmentListResponse> response) {
                if (response.isSuccessful()) {
                    TeacherFetchStudentAssignmentListResponse teacherFetchStudentAssignmentListResponse = response.body();
                    message.setValue(teacherFetchStudentAssignmentListResponse.getMessage());
                    list.setValue(teacherFetchStudentAssignmentListResponse.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<TeacherFetchStudentAssignmentListResponse> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<TeacherFetchStudentAssignmentListDetails>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
