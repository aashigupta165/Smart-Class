package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAddViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void scheduleCreate(String orgCode, String teacherCode, String className, String section, String topic, String subject, String date, String time,
                               ArrayList<String> students) {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().createSchedule(orgCode, teacherCode, className, section, topic,
                subject, date, time, students);
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
