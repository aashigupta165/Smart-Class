package com.education.smartclass.response;

import java.util.ArrayList;

public class FetchSubjectList {

    private ArrayList<String> list;
    private String message;

    public FetchSubjectList() {
    }

    public FetchSubjectList(ArrayList<String> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
