package com.education.smartclass.response;

import com.education.smartclass.models.AssignmentDetailsList;

import java.util.ArrayList;

public class AssignmentResponse {
    private String message;
    private ArrayList<AssignmentDetailsList> list;

    public AssignmentResponse() {
    }

    public AssignmentResponse(String message, ArrayList<AssignmentDetailsList> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AssignmentDetailsList> getList() {
        return list;
    }

    public void setList(ArrayList<AssignmentDetailsList> list) {
        this.list = list;
    }
}
