package com.education.smartclass.models;

import java.util.ArrayList;

public class StudnetCSVDataBody {

    private String role;
    private String orgCode;
    private String methodToCreate;
    private ArrayList<StudentCSVSampleData> list;

    public StudnetCSVDataBody() {
    }

    public StudnetCSVDataBody(String role, String orgCode, String methodToCreate, ArrayList<StudentCSVSampleData> list) {
        this.role = role;
        this.orgCode = orgCode;
        this.methodToCreate = methodToCreate;
        this.list = list;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMethodToCreate() {
        return methodToCreate;
    }

    public void setMethodToCreate(String methodToCreate) {
        this.methodToCreate = methodToCreate;
    }

    public ArrayList<StudentCSVSampleData> getList() {
        return list;
    }

    public void setList(ArrayList<StudentCSVSampleData> list) {
        this.list = list;
    }
}
