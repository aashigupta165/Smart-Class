package com.education.smartclass.models;

public class TeacherSubjects {

    private String id;
    private String subject;

    public TeacherSubjects() {
    }

    public TeacherSubjects(String id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "TeacherSubjects{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
