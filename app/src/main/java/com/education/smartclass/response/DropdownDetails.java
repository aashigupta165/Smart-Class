package com.education.smartclass.response;

import com.education.smartclass.models.ScheduleDetails;
import com.education.smartclass.models.TeacherClasses;

import java.util.ArrayList;

public class DropdownDetails {

    private ArrayList<TeacherClasses> teacherClass;
    private String message;

    public DropdownDetails() {
    }

    public DropdownDetails(ArrayList<TeacherClasses> teacherClass, String message) {
        this.teacherClass = teacherClass;
        this.message = message;
    }

    public ArrayList<TeacherClasses> getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(ArrayList<TeacherClasses> teacherClass) {
        this.teacherClass = teacherClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
