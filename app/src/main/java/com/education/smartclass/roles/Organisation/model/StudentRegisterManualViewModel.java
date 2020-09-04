package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegisterManualViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void register(String studentName, String studentRollNo, String studentClass, String studentSection, String studentFather,
                         String studentEmail, String mobile, String studentDOB, String studentGender, String orgCode) {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().registerStudent(studentName, studentRollNo, studentClass, studentSection,
                studentFather, "Student", studentEmail, mobile, studentDOB, studentGender, orgCode, "Manual");
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
