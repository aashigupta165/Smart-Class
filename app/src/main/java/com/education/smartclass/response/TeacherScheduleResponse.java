package com.education.smartclass.response;

import com.education.smartclass.models.ReadTeacherScheduleDetails;

import java.util.ArrayList;

public class TeacherScheduleResponse {

    private ArrayList<ReadTeacherScheduleDetails> list;
    private String message;

    public TeacherScheduleResponse() {
    }

    public TeacherScheduleResponse(ArrayList list, String message) {
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
