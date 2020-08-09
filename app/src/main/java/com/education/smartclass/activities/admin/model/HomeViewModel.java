package com.education.smartclass.activities.admin.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.OrganisationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText = new MutableLiveData<>();
    private OrganisationList organisationList;

    public OrganisationList fetchOrganisationList() {

        if (organisationList == null) {
            Call<OrganisationList> call = RetrofitClient.getInstance().getApi().showList("Admin", "Admin");
            call.enqueue(new Callback<OrganisationList>() {
                @Override
                public void onResponse(Call<OrganisationList> call, Response<OrganisationList> response) {
                    organisationList = response.body();
                    mText.setValue(organisationList.getMessage());
                }

                @Override
                public void onFailure(Call<OrganisationList> call, Throwable t) {
                    mText.setValue("Internet_Issue");
                }
            });
        }
        return organisationList;
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
