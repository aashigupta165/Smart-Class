package com.education.smartclass.models;

public class User {

    private String name;
    private String mobile;
    private String email;
    private String role;

    private String orgName;
    private String orgCode;
    private String orgType;
    private String orgAddress;
    private String orgLogo;
    private String orgEmail;
    private String orgMobile;

    public User() {
    }

    public User(String name, String mobile, String email, String role, String orgName, String orgCode, String orgType, String orgAddress, String orgLogo, String orgEmail, String orgMobile) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.role = role;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.orgType = orgType;
        this.orgAddress = orgAddress;
        this.orgLogo = orgLogo;
        this.orgEmail = orgEmail;
        this.orgMobile = orgMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgMobile() {
        return orgMobile;
    }

    public void setOrgMobile(String orgMobile) {
        this.orgMobile = orgMobile;
    }
}
