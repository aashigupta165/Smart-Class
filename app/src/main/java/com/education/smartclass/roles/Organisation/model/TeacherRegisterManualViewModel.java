package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherRegisterManualViewModel extends ViewModel {
    private MutableLiveData<String> message = new MutableLiveData<>();

    public void register(String teacherName, String teacherAge, String teacherDesignation, String teacherCode, String teacherGender,
                         String teacherEmail, String mobile, ArrayList<String> class_section_subject, String orgCode) {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().registerTeacher(teacherName, teacherAge, teacherDesignation, teacherCode,
                teacherGender, "Teacher", teacherEmail, mobile, class_section_subject, orgCode, "Manual");
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
