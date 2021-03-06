package com.education.smartclass.modules.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.Teachers;
import com.education.smartclass.response.TeacherList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Teachers>> list = new MutableLiveData<>();

    public void fetchTeacherList(String orgCode) {

        Call<TeacherList> call = RetrofitClient.getInstance().getApi().showTeacherList("Organisation", orgCode);
        call.enqueue(new Callback<TeacherList>() {
            @Override
            public void onResponse(Call<TeacherList> call, Response<TeacherList> response) {
                if (response.isSuccessful()) {
                    TeacherList teacherList = response.body();
                    message.setValue(teacherList.getMessage());
                    list.setValue(teacherList.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<TeacherList> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<Teachers>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
