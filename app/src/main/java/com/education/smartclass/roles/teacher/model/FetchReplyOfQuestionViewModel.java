package com.education.smartclass.roles.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.Reply;
import com.education.smartclass.response.FetchRepliesOfAQuestion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchReplyOfQuestionViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Reply>> list = new MutableLiveData<>();

    public void fetchReplies(String orgCode, String questionId) {

        Call<FetchRepliesOfAQuestion> call = RetrofitClient.getInstance().getApi().fetchQuestionreply(orgCode, questionId);
        call.enqueue(new Callback<FetchRepliesOfAQuestion>() {
            @Override
            public void onResponse(Call<FetchRepliesOfAQuestion> call, Response<FetchRepliesOfAQuestion> response) {
                if (response.isSuccessful()) {
                    FetchRepliesOfAQuestion fetchRepliesOfAQuestion = response.body();
                    message.setValue(fetchRepliesOfAQuestion.getMessage());
                    list.setValue(fetchRepliesOfAQuestion.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<FetchRepliesOfAQuestion> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<Reply>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
