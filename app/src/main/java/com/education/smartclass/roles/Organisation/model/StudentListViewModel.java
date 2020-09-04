package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.StudentDetail;
import com.education.smartclass.response.StudentList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StudentDetail>> list = new MutableLiveData<>();

    public void fetchStudentList(String orgCode, String need) {

        Call<StudentList> call = RetrofitClient.getInstance().getApi().showStudentsList(orgCode, need);
        call.enqueue(new Callback<StudentList>() {
            @Override
            public void onResponse(Call<StudentList> call, Response<StudentList> response) {
                if (response.isSuccessful()) {
                    StudentList studentList = response.body();
                    message.setValue(studentList.getMessage());
                    list.setValue(studentList.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<StudentList> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<StudentDetail>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
