package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.response.DropdownDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDropdownDetailsViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TeacherClasses>> list = new MutableLiveData<>();

    public void fetchDropdownDetails(String orgCode, String teacherCode) {

        Call<DropdownDetails> call = RetrofitClient.getInstance().getApi().fetchDropdown(orgCode, teacherCode);
        call.enqueue(new Callback<DropdownDetails>() {
            @Override
            public void onResponse(Call<DropdownDetails> call, Response<DropdownDetails> response) {
                if (response.isSuccessful()) {
                    DropdownDetails dropdownDetails = response.body();
                    message.setValue(dropdownDetails.getMessage());
                    list.setValue(dropdownDetails.getTeacherClass());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<DropdownDetails> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<TeacherClasses>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
