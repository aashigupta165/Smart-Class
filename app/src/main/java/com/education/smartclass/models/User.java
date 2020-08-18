package com.education.smartclass.models;

import java.util.ArrayList;

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

//    private String teacherName;
//    private String teacherAge;
//    private String teacherDesignation;
    private String teacherCode;
//    private String teacherGender;
    private String teacherEmail;
    private String teacherMobile;
//    private ArrayList<TeacherClasses> teacherClasses;

    public User() {
    }

    public User(String name, String mobile, String email, String role, String orgName, String orgCode, String orgType, String orgAddress, String orgLogo, String orgEmail, String orgMobile, String teacherCode, String teacherEmail, String teacherMobile) {
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
//        this.teacherName = teacherName;
//        this.teacherAge = teacherAge;
//        this.teacherDesignation = teacherDesignation;
        this.teacherCode = teacherCode;
//        this.teacherGender = teacherGender;
        this.teacherEmail = teacherEmail;
        this.teacherMobile = teacherMobile;
//        this.teacherClasses = teacherClasses;
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

//    public String getTeacherName() {
//        return teacherName;
//    }
//
//    public void setTeacherName(String teacherName) {
//        this.teacherName = teacherName;
//    }

//    public String getTeacherAge() {
//        return teacherAge;
//    }
//
//    public void setTeacherAge(String teacherAge) {
//        this.teacherAge = teacherAge;
//    }
//
//    public String getTeacherDesignation() {
//        return teacherDesignation;
//    }
//
//    public void setTeacherDesignation(String teacherDesignation) {
//        this.teacherDesignation = teacherDesignation;
//    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

//    public String getTeacherGender() {
//        return teacherGender;
//    }
//
//    public void setTeacherGender(String teacherGender) {
//        this.teacherGender = teacherGender;
//    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherMobile() {
        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {
        this.teacherMobile = teacherMobile;
    }

//    public ArrayList<TeacherClasses> getTeacherClasses() {
//        return teacherClasses;
//    }
//
//    public void setTeacherClasses(ArrayList<TeacherClasses> teacherClasses) {
//        this.teacherClasses = teacherClasses;
//    }
}
