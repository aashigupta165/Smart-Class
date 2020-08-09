package com.education.smartclass.models;

public class Organisation {
    private String orgName;
    private String orgCode;

    public Organisation() {
    }

    public Organisation(String orgName, String orgCode) {
        this.orgName = orgName;
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
