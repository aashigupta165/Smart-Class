package com.education.smartclass.models;

import java.util.ArrayList;

public class OrgClassList {

    private ArrayList<OrgSubjects> orgSubjects;
    private String orgClass;
    private String orgSection;

    public OrgClassList() {
    }

    public OrgClassList(ArrayList<OrgSubjects> orgSubjects, String orgClass, String orgSection) {
        this.orgSubjects = orgSubjects;
        this.orgClass = orgClass;
        this.orgSection = orgSection;
    }

    public ArrayList<OrgSubjects> getOrgSubjects() {
        return orgSubjects;
    }

    public void setOrgSubjects(ArrayList<OrgSubjects> orgSubjects) {
        this.orgSubjects = orgSubjects;
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
}
