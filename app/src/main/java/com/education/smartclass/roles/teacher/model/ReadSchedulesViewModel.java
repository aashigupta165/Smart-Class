package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.ReadScheduleDetails;
import com.education.smartclass.response.ScheduleResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadSchedulesViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ReadScheduleDetails>> list = new MutableLiveData<>();

    public void fetchScheduleList(String orgCode, String teacherCode) {

        Call<ScheduleResponse> call = RetrofitClient.getInstance().getApi().readSchedule(orgCode, "Teacher", teacherCode);
        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                ScheduleResponse scheduleResponse = response.body();
                message.setValue(scheduleResponse.getMessage());
                list.setValue(scheduleResponse.getList());
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<ReadScheduleDetails>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
