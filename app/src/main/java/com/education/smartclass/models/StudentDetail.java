package com.education.smartclass.models;

public class StudentDetail {

    private String studentName;
    private String studentRollNo;
    private String studentEmail;

    public StudentDetail() {
    }

    public StudentDetail(String studentName, String studentRollNo, String studentEmail) {
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
        this.studentEmail = studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
