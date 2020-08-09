package com.education.smartclass.activities.admin.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<String> mText = new MutableLiveData<>();

    public void RegisterNewOrg() {

//        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().register("Admin", "Admin");
//        call.enqueue(new Callback<MessageResponse>() {
//            @Override
//            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
//                MessageResponse messageResponse = response.body();
//                mText.setValue(messageResponse.getMessage());
//            }
//
//            @Override
//            public void onFailure(Call<MessageResponse> call, Throwable t) {
//                mText.setValue("Internet_Issue");
//            }
//        });
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
