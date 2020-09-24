package com.education.smartclass.response;

import com.education.smartclass.models.TeacherFetchStudentAssignmentListDetails;

import java.util.ArrayList;

public class TeacherFetchStudentAssignmentListResponse {

    ArrayList<TeacherFetchStudentAssignmentListDetails> list;
    private String message;

    public TeacherFetchStudentAssignmentListResponse() {
    }

    public TeacherFetchStudentAssignmentListResponse(ArrayList<TeacherFetchStudentAssignmentListDetails> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<TeacherFetchStudentAssignmentListDetails> getList() {
        return list;
    }

    public void setList(ArrayList<TeacherFetchStudentAssignmentListDetails> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
