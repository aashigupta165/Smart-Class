package com.education.smartclass.models;

public class StudentCSVSampleData {

    private String sno;
    private String name;
    private String rollNo;
    private String studentClass;
    private String studentSection;
    private String fatherName;
    private String role;
    private String email;
    private String mobile;
    private String dob;
    private String gender;

    public StudentCSVSampleData() {
    }

    public StudentCSVSampleData(String sno, String name, String rollNo, String studentClass, String studentSection, String fatherName, String role, String email, String mobile, String dob, String gender) {
        this.sno = sno;
        this.name = name;
        this.rollNo = rollNo;
        this.studentClass = studentClass;
        this.studentSection = studentSection;
        this.fatherName = fatherName;
        this.role = role;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.gender = gender;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentSection() {
        return studentSection;
    }

    public void setStudentSection(String studentSection) {
        this.studentSection = studentSection;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
