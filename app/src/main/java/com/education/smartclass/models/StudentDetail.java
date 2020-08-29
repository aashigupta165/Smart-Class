package com.education.smartclass.models;

public class StudentDetail {

    private String studentName;
    private String studentRollNo;
    private String studentClass;
    private String studentSection;
    private String studentEmail;

    public StudentDetail() {
    }

    public StudentDetail(String studentName, String studentRollNo, String studentClass, String studentSection, String studentEmail) {
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
        this.studentClass = studentClass;
        this.studentSection = studentSection;
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
}
