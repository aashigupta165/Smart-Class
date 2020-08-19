package com.education.smartclass.response;

import com.education.smartclass.models.TeacherClasses;

import java.util.ArrayList;

public class DropdownDetails {

    private ArrayList<TeacherClasses> teacherClasses;
    private String message;

    public DropdownDetails() {
    }

    public DropdownDetails(ArrayList<TeacherClasses> teacherClasses, String message) {
        this.teacherClasses = teacherClasses;
        this.message = message;
    }

    public ArrayList<TeacherClasses> getTeacherClasses() {
        return teacherClasses;
    }

    public void setTeacherClasses(ArrayList<TeacherClasses> teacherClasses) {
        this.teacherClasses = teacherClasses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
