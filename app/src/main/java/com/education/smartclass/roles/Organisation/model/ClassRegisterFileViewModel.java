package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.ClassCSVDataBody;
import com.education.smartclass.models.ClassCSVSampleData;
import com.education.smartclass.response.MessageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassRegisterFileViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void register(String orgCode, ArrayList<ClassCSVSampleData> list) {

        ClassCSVDataBody classCSVDataBody = new ClassCSVDataBody();
        classCSVDataBody.setRole("Class");
        classCSVDataBody.setOrgCode(orgCode);
        classCSVDataBody.setMethodToCreate("File");
        classCSVDataBody.setList(list);

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().registerClasses(classCSVDataBody);
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
