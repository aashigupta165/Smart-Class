package com.education.smartclass.modules.admin.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.Organisation;
import com.education.smartclass.response.OrganisationList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Organisation>> list = new MutableLiveData<>();

    public void fetchOrganisationList() {

        Call<OrganisationList> call = RetrofitClient.getInstance().getApi().showList("Admin", "Admin");
        call.enqueue(new Callback<OrganisationList>() {
            @Override
            public void onResponse(Call<OrganisationList> call, Response<OrganisationList> response) {
                if (response.isSuccessful()) {
                    OrganisationList organisationList = response.body();
                    message.setValue(organisationList.getMessage());
                    list.setValue(organisationList.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<OrganisationList> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<Organisation>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
