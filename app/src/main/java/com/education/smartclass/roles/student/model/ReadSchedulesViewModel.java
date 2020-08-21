package com.education.smartclass.roles.student.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.ReadStudentScheduleDetails;
import com.education.smartclass.response.StudentScheduleResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadSchedulesViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ReadStudentScheduleDetails>> list = new MutableLiveData<>();

    public void fetchScheduleList(String orgCode, String studentClass, String studentSection, String studentRollNo) {

        Call<StudentScheduleResponse> call = RetrofitClient.getInstance().getApi().readStudentSchedule(orgCode, "Student", studentClass, studentSection,
                studentRollNo);
        call.enqueue(new Callback<StudentScheduleResponse>() {
            @Override
            public void onResponse(Call<StudentScheduleResponse> call, Response<StudentScheduleResponse> response) {
                StudentScheduleResponse studentScheduleResponse = response.body();
                message.setValue(studentScheduleResponse.getMessage());
                list.setValue(studentScheduleResponse.getList());
            }

            @Override
            public void onFailure(Call<StudentScheduleResponse> call, Throwable t) {
                message.setValue("Internet_Issue");
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<ReadStudentScheduleDetails>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
