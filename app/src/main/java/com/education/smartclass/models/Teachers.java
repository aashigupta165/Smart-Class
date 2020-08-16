package com.education.smartclass.models;

public class Teachers {

    private String teacherName;
    private String teacherCode;
    private String active;

    public Teachers() {
    }

    public Teachers(String teacherName, String teacherCode, String active) {
        this.teacherName = teacherName;
        this.teacherCode = teacherCode;
        this.active = active;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
