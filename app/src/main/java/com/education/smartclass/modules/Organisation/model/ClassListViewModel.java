package com.education.smartclass.modules.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.OrgClassList;
import com.education.smartclass.response.ClassList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassListViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<OrgClassList>> list = new MutableLiveData<>();

    public void fetchStudentList(String orgCode, String need) {

        Call<ClassList> call = RetrofitClient.getInstance().getApi().showClassList(orgCode, need);
        call.enqueue(new Callback<ClassList>() {
            @Override
            public void onResponse(Call<ClassList> call, Response<ClassList> response) {
                if (response.isSuccessful()) {
                    ClassList classList = response.body();
                    message.setValue(classList.getMessage());
                    list.setValue(classList.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<ClassList> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<OrgClassList>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
