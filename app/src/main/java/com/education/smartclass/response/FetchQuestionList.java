package com.education.smartclass.response;

import com.education.smartclass.models.Question;

import java.util.ArrayList;

public class FetchQuestionList {

    private ArrayList<Question> list;
    private String message;

    public FetchQuestionList() {
    }

    public FetchQuestionList(ArrayList<Question> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<Question> getList() {
        return list;
    }

    public void setList(ArrayList<Question> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
