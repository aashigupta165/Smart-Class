package com.education.smartclass.roles.login.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.LoginResponse;
import com.education.smartclass.roles.login.activities.LoginActivity;
import com.education.smartclass.storage.SharedPrefManager;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void dataRetrieval(Context context, String mobile, String password) {

        String token = FirebaseInstanceId.getInstance().getToken();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(mobile, password, token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (loginResponse.getMessage().equals("loggedIn")) {
                    SharedPrefManager.getInstance(context).saveUser(loginResponse.getUser());
                }

                message.setValue(loginResponse.getMessage());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                message.setValue(t.getMessage());
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
