package com.education.smartclass.models;

public class OrgSubjects {

    private String id;
    private String subjects;

    public OrgSubjects() {
    }

    public OrgSubjects(String id, String subjects) {
        this.id = id;
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subjects;
    }

    public void setSubject(String subjects) {
        this.subjects = subjects;
    }
}
