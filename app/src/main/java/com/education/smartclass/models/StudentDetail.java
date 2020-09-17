package com.education.smartclass.models;

public class StudentDetail {

    private String studentName;
    private String studentRollNo;
    private String studentClass;
    private String studentSection;
    private String studentId;

    public StudentDetail() {
    }

    public StudentDetail(String studentName, String studentRollNo, String studentClass, String studentSection, String studentId) {
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
        this.studentClass = studentClass;
        this.studentSection = studentSection;
        this.studentId = studentId;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
