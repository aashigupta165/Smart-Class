package com.education.smartclass.response;

import com.education.smartclass.models.ReadStudentScheduleDetails;

import java.util.ArrayList;

public class StudentScheduleResponse {

    private ArrayList<ReadStudentScheduleDetails> list;
    private String message;

    public StudentScheduleResponse() {
    }

    public StudentScheduleResponse(ArrayList list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
