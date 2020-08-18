package com.education.smartclass.models;

import java.util.ArrayList;

public class TeacherClasses {

    private ArrayList<TeacherSubjects> teacherSubjects;
    private String id;
    private String teacherClass;
    private String teacherSection;

    public TeacherClasses() {
    }

    public TeacherClasses(ArrayList<TeacherSubjects> teacherSubjects, String id, String teacherClass, String teacherSection) {
        this.teacherSubjects = teacherSubjects;
        this.id = id;
        this.teacherClass = teacherClass;
        this.teacherSection = teacherSection;
    }

    public ArrayList<TeacherSubjects> getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(ArrayList<TeacherSubjects> teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(String teacherClass) {
        this.teacherClass = teacherClass;
    }

    public String getTeacherSection() {
        return teacherSection;
    }

    public void setTeacherSection(String teacherSection) {
        this.teacherSection = teacherSection;
    }
}
