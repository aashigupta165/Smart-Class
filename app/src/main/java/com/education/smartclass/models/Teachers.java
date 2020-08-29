package com.education.smartclass.models;

import java.util.ArrayList;

public class Teachers {

    private String teacherName;
    private String teacherCode;
    private ArrayList<TeacherClasses> teacherClass;
    private String active;

    public Teachers() {
    }

    public Teachers(String teacherName, String teacherCode, ArrayList<TeacherClasses> teacherClass, String active) {
        this.teacherName = teacherName;
        this.teacherCode = teacherCode;
        this.teacherClass = teacherClass;
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

    public ArrayList<TeacherClasses> getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(ArrayList<TeacherClasses> teacherClass) {
        this.teacherClass = teacherClass;
    }

    @Override
    public String toString() {
        return "Teachers{" +
                "teacherName='" + teacherName + '\'' +
                ", teacherCode='" + teacherCode + '\'' +
                ", teacherClass=" + teacherClass +
                ", active='" + active + '\'' +
                '}';
    }
}
