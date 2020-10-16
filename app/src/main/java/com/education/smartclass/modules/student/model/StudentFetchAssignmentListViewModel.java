package com.education.smartclass.modules.student.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.AssignmentDetailsList;
import com.education.smartclass.response.AssignmentResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFetchAssignmentListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<AssignmentDetailsList>> list = new MutableLiveData<>();

    public void fetchAssignmentList(String orgCode, String studentClass, String studentSection, String studentId) {

        Call<AssignmentResponse> call = RetrofitClient.getInstance().getApi().studentFetchAssignmentList(orgCode, "Student", studentClass, studentSection, studentId);
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()) {
                    AssignmentResponse teacherAssignmentResponse = response.body();
                    message.setValue(teacherAssignmentResponse.getMessage());
                    list.setValue(teacherAssignmentResponse.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<AssignmentDetailsList>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
