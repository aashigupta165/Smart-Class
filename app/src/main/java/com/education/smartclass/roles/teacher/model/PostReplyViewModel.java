package com.education.smartclass.roles.teacher.model;

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

public class PostReplyViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();

    public void postReply(String orgCode, String questionId, String reply, String name, String role, String code,
                             String className, String section) {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String now = dateFormat.format(date);

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().replyToQuestion(orgCode, questionId, reply, name, role, code, className, section, now);
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
