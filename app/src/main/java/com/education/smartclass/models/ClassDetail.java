package com.education.smartclass.models;

import java.io.Serializable;

public class ClassDetail implements Serializable {

    private String className;
    private String section;
    private String subject;

    public ClassDetail() {
    }

    public ClassDetail(String className, String section, String subject) {
        this.className = className;
        this.section = section;
        this.subject = subject;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
