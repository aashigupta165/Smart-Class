package com.education.smartclass.response;

import com.education.smartclass.models.OrgClassList;

import java.util.ArrayList;

public class ClassList {

    private ArrayList<OrgClassList> list;
    private String message;

    public ClassList() {
    }

    public ClassList(ArrayList<OrgClassList> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<OrgClassList> getList() {
        return list;
    }

    public void setList(ArrayList<OrgClassList> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
