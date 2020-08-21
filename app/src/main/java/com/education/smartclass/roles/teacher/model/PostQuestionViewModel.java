package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostQuestionViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void postQuestion(String orgCode, String purposeOfQuestion, String subject, String question, String questionAskerName, String questionAskerCode,
                             String questionAskerRole, String questionForClass, String questionForSection) {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().teacherPostQuestion(orgCode, purposeOfQuestion, subject, question, questionAskerName,
                questionAskerCode, questionAskerRole, questionForClass, questionForSection);
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
