package com.education.smartclass.roles.Organisation.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherRegisterFileViewModel extends ViewModel {
    private MutableLiveData<String> message = new MutableLiveData<>();

    public void register(String orgCode, MultipartBody.Part file) {

        RequestBody Role = RequestBody.create("Teacher", MediaType.parse("text/plain"));
        RequestBody OrgCode = RequestBody.create(orgCode, MediaType.parse("text/plain"));
        RequestBody methodToCreate = RequestBody.create("File", MediaType.parse("text/plain"));

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().registerTeachers(Role, OrgCode, methodToCreate, file);
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
