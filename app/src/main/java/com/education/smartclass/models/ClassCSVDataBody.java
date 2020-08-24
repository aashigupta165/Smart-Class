package com.education.smartclass.models;

import java.util.ArrayList;

public class ClassCSVDataBody {

    private String role;
    private String orgCode;
    private String methodToCreate;
    private ArrayList<ClassCSVSampleData> list;

    public ClassCSVDataBody() {
    }

    public ClassCSVDataBody(String role, String orgCode, String methodToCreate, ArrayList<ClassCSVSampleData> list) {
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

    public ArrayList<ClassCSVSampleData> getList() {
        return list;
    }

    public void setList(ArrayList<ClassCSVSampleData> list) {
        this.list = list;
    }
}
