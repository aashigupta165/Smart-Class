package com.education.smartclass.roles.teacher.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.TeacherClasses;
import com.education.smartclass.response.DropdownDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDropdownDetailsViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<List<TeacherClasses>> list = new MutableLiveData<>();

    public void fetchDropdoenDetails(Context context, String mobile, String password) {

        Call<DropdownDetails> call = RetrofitClient.getInstance().getApi().fetchDropdown(mobile, password);
        call.enqueue(new Callback<DropdownDetails>() {
            @Override
            public void onResponse(Call<DropdownDetails> call, Response<DropdownDetails> response) {
                DropdownDetails dropdownDetails = response.body();
                message.setValue(dropdownDetails.getMessage());
                list.setValue(dropdownDetails.getTeacherClasses());
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

    public MutableLiveData<List<TeacherClasses>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
