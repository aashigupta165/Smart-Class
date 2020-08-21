package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.ReadTeacherScheduleDetails;
import com.education.smartclass.response.TeacherScheduleResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadSchedulesViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ReadTeacherScheduleDetails>> list = new MutableLiveData<>();

    public void fetchScheduleList(String orgCode, String teacherCode) {

        Call<TeacherScheduleResponse> call = RetrofitClient.getInstance().getApi().readTeacherSchedule(orgCode, "Teacher", teacherCode);
        call.enqueue(new Callback<TeacherScheduleResponse>() {
            @Override
            public void onResponse(Call<TeacherScheduleResponse> call, Response<TeacherScheduleResponse> response) {
                TeacherScheduleResponse teacherScheduleResponse = response.body();
                message.setValue(teacherScheduleResponse.getMessage());
                list.setValue(teacherScheduleResponse.getList());
            }

            @Override
            public void onFailure(Call<TeacherScheduleResponse> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<ReadTeacherScheduleDetails>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
