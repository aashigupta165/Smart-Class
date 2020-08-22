package com.education.smartclass.roles.admin.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void RegisterNewOrg(String orgName, String orgCode, String orgType, String orgAddress, String email, String mobile, String isLogo, MultipartBody.Part logo) {

        RequestBody OrgName = RequestBody.create(orgName, MediaType.parse("text/plain"));
        RequestBody OrgCode = RequestBody.create(orgCode, MediaType.parse("text/plain"));
        RequestBody OrgType = RequestBody.create(orgType, MediaType.parse("text/plain"));
        RequestBody OrgAddress = RequestBody.create(orgAddress, MediaType.parse("text/plain"));
        RequestBody Email = RequestBody.create(email, MediaType.parse("text/plain"));
        RequestBody Role = RequestBody.create("Organisation", MediaType.parse("text/plain"));
        RequestBody Mobile = RequestBody.create(mobile, MediaType.parse("text/plain"));
        RequestBody MethodToUpload = RequestBody.create(isLogo, MediaType.parse("text/plain"));

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().registerOrg(OrgName, OrgCode, OrgType, OrgAddress, Email, Role, Mobile,
                MethodToUpload, logo);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                message.setValue(messageResponse.getMessage());
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
