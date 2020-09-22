package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.TeacherAssignmentDetailsList;
import com.education.smartclass.response.TeacherAssignmentResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherFetchAssignmentListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TeacherAssignmentDetailsList>> list = new MutableLiveData<>();

    public void fetchAssignmentLists(String orgCode, String teacherCode) {

        Call<TeacherAssignmentResponse> call = RetrofitClient.getInstance().getApi().teacherFetchAssignmentList(orgCode, "Teacher", teacherCode);
        call.enqueue(new Callback<TeacherAssignmentResponse>() {
            @Override
            public void onResponse(Call<TeacherAssignmentResponse> call, Response<TeacherAssignmentResponse> response) {
                if (response.isSuccessful()) {
                    TeacherAssignmentResponse teacherAssignmentResponse = response.body();
                    message.setValue(teacherAssignmentResponse.getMessage());
                    list.setValue(teacherAssignmentResponse.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<TeacherAssignmentResponse> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<TeacherAssignmentDetailsList>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
