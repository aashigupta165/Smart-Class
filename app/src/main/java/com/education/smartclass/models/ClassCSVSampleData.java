package com.education.smartclass.models;

public class ClassCSVSampleData {

    private String sno;
    private String orgClass;
    private String orgSection;
    private String subjects;

    public ClassCSVSampleData() {
    }

    public ClassCSVSampleData(String sno, String orgClass, String orgSection, String subjects) {
        this.sno = sno;
        this.orgClass = orgClass;
        this.orgSection = orgSection;
        this.subjects = subjects;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getOrgClass() {
        return orgClass;
    }

    public void setOrgClass(String orgClass) {
        this.orgClass = orgClass;
    }

    public String getOrgSection() {
        return orgSection;
    }

    public void setOrgSection(String orgSection) {
        this.orgSection = orgSection;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }
}
