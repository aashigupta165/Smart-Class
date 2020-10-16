package com.education.smartclass.modules.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.response.MessageResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostQuestionViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void postQuestion(String orgCode, String purposeOfQuestion, String subject, String question, String questionAskerName, String questionAskerCode,
                             String questionAskerRole, String questionForClass, String questionForSection) {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String now = dateFormat.format(date);

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().teacherPostQuestion(orgCode, purposeOfQuestion, subject, question, questionAskerName,
                questionAskerCode, questionAskerRole, questionForClass, questionForSection, now);
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
