package com.education.smartclass.modules.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.AssignmentDetailsList;
import com.education.smartclass.response.AssignmentResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherFetchAssignmentListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<AssignmentDetailsList>> list = new MutableLiveData<>();

    public void fetchAssignmentLists(String orgCode, String teacherCode) {

        Call<AssignmentResponse> call = RetrofitClient.getInstance().getApi().teacherFetchAssignmentList(orgCode, "Teacher", teacherCode);
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()) {
                    AssignmentResponse assignmentResponse = response.body();
                    message.setValue(assignmentResponse.getMessage());
                    list.setValue(assignmentResponse.getList());
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
