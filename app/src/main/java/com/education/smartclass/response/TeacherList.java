package com.education.smartclass.response;

import com.education.smartclass.models.Teachers;

import java.util.ArrayList;

public class TeacherList {

    private ArrayList<Teachers> list;
    private String message;
    private String active;

    public TeacherList(ArrayList<Teachers> list, String message, String active) {
        this.list = list;
        this.message = message;
        this.active = active;
    }

    public TeacherList() {
    }

    public ArrayList<Teachers> getList() {
        return list;
    }

    public void setList(ArrayList<Teachers> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TeacherList{" +
                "list=" + list +
                ", message='" + message + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
