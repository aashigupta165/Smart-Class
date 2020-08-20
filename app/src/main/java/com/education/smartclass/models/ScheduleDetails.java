package com.education.smartclass.models;

import java.util.ArrayList;

public class ScheduleDetails {

    private ArrayList<TeacherClasses> teacherClasses;

    public ScheduleDetails() {
    }

    public ScheduleDetails(ArrayList<TeacherClasses> teacherClasses, String message) {
        this.teacherClasses = teacherClasses;
    }

    public ArrayList<TeacherClasses> getTeacherClasses() {
        return teacherClasses;
    }

    public void setTeacherClasses(ArrayList<TeacherClasses> teacherClasses) {
        this.teacherClasses = teacherClasses;
    }
}
