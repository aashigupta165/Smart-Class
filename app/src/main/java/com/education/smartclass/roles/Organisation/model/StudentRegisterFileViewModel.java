package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.StudentCSVDataBody;
import com.education.smartclass.models.StudentCSVSampleData;
import com.education.smartclass.response.MessageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegisterFileViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void register(String orgCode, ArrayList<StudentCSVSampleData> list) {

        StudentCSVDataBody studentCSVDataBody = new StudentCSVDataBody();
        studentCSVDataBody.setRole("Student");
        studentCSVDataBody.setOrgCode(orgCode);
        studentCSVDataBody.setMethodToCreate("File");
        studentCSVDataBody.setList(list);

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().registerStudents(studentCSVDataBody);
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
