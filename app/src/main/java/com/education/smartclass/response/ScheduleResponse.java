package com.education.smartclass.response;

import com.education.smartclass.models.ReadScheduleDetails;

import java.util.ArrayList;

public class ScheduleResponse {

    private ArrayList<ReadScheduleDetails> list;
    private String message;

    public ScheduleResponse() {
    }

    public ScheduleResponse(ArrayList list, String message) {
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
