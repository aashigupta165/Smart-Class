package com.education.smartclass.activities.login.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.LoginResponse;
import com.education.smartclass.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> mText = new MutableLiveData<>();

    public void dataRetrieval(Context context, String mobile, String password) {

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(mobile, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                SharedPrefManager.getInstance(context).saveUser(loginResponse.getUser());
                mText.setValue(loginResponse.getMessage());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mText.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
