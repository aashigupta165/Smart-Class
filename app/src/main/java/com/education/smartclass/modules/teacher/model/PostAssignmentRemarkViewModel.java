package com.education.smartclass.modules.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAssignmentRemarkViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void postRemark(String orgCode, String teacherCode, String assignmentId, String studentId, String teacherRemark) {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().postRemark(orgCode, teacherCode, assignmentId, studentId, teacherRemark);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    message.setValue(messageResponse.getMessage());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
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
