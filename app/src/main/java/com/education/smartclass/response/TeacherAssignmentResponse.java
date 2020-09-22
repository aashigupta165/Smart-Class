package com.education.smartclass.response;

import com.education.smartclass.models.TeacherAssignmentDetailsList;

import java.util.ArrayList;

public class TeacherAssignmentResponse {
    private String message;
    private ArrayList<TeacherAssignmentDetailsList> list;

    public TeacherAssignmentResponse() {
    }

    public TeacherAssignmentResponse(String message, ArrayList<TeacherAssignmentDetailsList> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TeacherAssignmentDetailsList> getList() {
        return list;
    }

    public void setList(ArrayList<TeacherAssignmentDetailsList> list) {
        this.list = list;
    }
}
