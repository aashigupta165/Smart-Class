package com.education.smartclass.models;

import java.util.ArrayList;

public class TeacherClasses {

    private ArrayList<TeacherSubjects> teachingSubjects;
    private String teacherClass;
    private String teacherSection;

    public TeacherClasses() {
    }

    public TeacherClasses(ArrayList<TeacherSubjects> teachingSubjects, String teacherClass, String teacherSection) {
        this.teachingSubjects = teachingSubjects;
        this.teacherClass = teacherClass;
        this.teacherSection = teacherSection;
    }

    public ArrayList<TeacherSubjects> getTeachingSubjects() {
        return teachingSubjects;
    }

    public void setTeachingSubjects(ArrayList<TeacherSubjects> teachingSubjects) {
        this.teachingSubjects = teachingSubjects;
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

    @Override
    public String toString() {
        return "TeacherClasses{" +
                "teachingSubjects=" + teachingSubjects +
                ", teacherClass='" + teacherClass + '\'' +
                ", teacherSection='" + teacherSection + '\'' +
                '}';
    }
}
