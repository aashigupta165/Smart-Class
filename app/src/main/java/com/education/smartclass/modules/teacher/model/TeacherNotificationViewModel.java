package com.education.smartclass.modules.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.FetchSubjectList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherNotificationViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> list = new MutableLiveData<>();

    public void fetchNotifications(String orgCode, String role, String teacherCode) {

        Call<FetchSubjectList> call = RetrofitClient.getInstance().getApi().notificationTeacher(orgCode, role, teacherCode);
        call.enqueue(new Callback<FetchSubjectList>() {
            @Override
            public void onResponse(Call<FetchSubjectList> call, Response<FetchSubjectList> response) {
                if (response.isSuccessful()) {
                    FetchSubjectList fetchSubjectList = response.body();
                    message.setValue(fetchSubjectList.getMessage());
                    list.setValue(fetchSubjectList.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<FetchSubjectList> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<String>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
