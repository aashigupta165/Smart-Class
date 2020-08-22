package com.education.smartclass.response;

import com.education.smartclass.models.Reply;

import java.util.ArrayList;

public class FetchRepliesOfAQuestion {

    private ArrayList<Reply> list;
    private String message;

    public FetchRepliesOfAQuestion() {
    }

    public FetchRepliesOfAQuestion(ArrayList<Reply> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<Reply> getList() {
        return list;
    }

    public void setList(ArrayList<Reply> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
