package com.education.smartclass.response;

import com.education.smartclass.models.Organisation;

import java.util.ArrayList;

public class OrganisationList {
    private ArrayList<Organisation> list;
    private String message;

    public OrganisationList() {
    }

    public OrganisationList(ArrayList<Organisation> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<Organisation> getList() {
        return list;
    }

    public void setList(ArrayList<Organisation> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
