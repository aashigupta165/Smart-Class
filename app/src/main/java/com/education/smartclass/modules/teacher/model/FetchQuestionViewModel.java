package com.education.smartclass.modules.teacher.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.education.smartclass.api.RetrofitClient;
import com.education.smartclass.models.Question;
import com.education.smartclass.response.FetchQuestionList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionViewModel extends ViewModel {

    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Question>> list = new MutableLiveData<>();

    public void fetchQuestions(String orgCode) {

        Call<FetchQuestionList> call = RetrofitClient.getInstance().getApi().fetchQuestionList(orgCode);
        call.enqueue(new Callback<FetchQuestionList>() {
            @Override
            public void onResponse(Call<FetchQuestionList> call, Response<FetchQuestionList> response) {
                if (response.isSuccessful()) {
                    FetchQuestionList fetchQuestionList = response.body();
                    message.setValue(fetchQuestionList.getMessage());
                    list.setValue(fetchQuestionList.getList());
                } else if (response.code() == 401) {
                    message.setValue("Session Expire");
                }
            }

            @Override
            public void onFailure(Call<FetchQuestionList> call, Throwable t) {
                message.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<ArrayList<Question>> getList() {
        return list;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
