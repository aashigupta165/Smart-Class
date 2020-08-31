package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleCreateViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void scheduleCreate(String orgCode, String teacherCode, String className, String section, String topic, String count, String date, String time,
                               String description, ArrayList<String> students) {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().createScheduleOrganisation(orgCode, teacherCode, className, section, topic, count,
                date, time, description, students);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                message.setValue(messageResponse.getMessage());
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
