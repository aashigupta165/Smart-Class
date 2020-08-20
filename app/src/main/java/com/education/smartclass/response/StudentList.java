package com.education.smartclass.response;

import com.education.smartclass.models.StudentDetail;

import java.util.ArrayList;

public class StudentList {

    ArrayList<StudentDetail> list;
    private String message;

    public StudentList() {
    }

    public StudentList(ArrayList<StudentDetail> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<StudentDetail> getList() {
        return list;
    }

    public void setList(ArrayList<StudentDetail> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
