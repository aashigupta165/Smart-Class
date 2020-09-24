package com.education.smartclass.models;

public class TeacherFetchStudentAssignmentListDetails {
    private String studentName;
    private String studentRollNo;
    private String studentId;
    private String teacherRemark;
    private String responseActive;
    private String studentDescription;
    private String studentFile;
    private String studentDate;
    private String studentTime;

    public TeacherFetchStudentAssignmentListDetails() {
    }

    public TeacherFetchStudentAssignmentListDetails(String studentName, String studentRollNo, String studentId, String teacherRemark, String responseActive, String studentDescription, String studentFile, String studentDate, String studentTime) {
        this.studentName = studentName;
        this.studentRollNo = studentRollNo;
        this.studentId = studentId;
        this.teacherRemark = teacherRemark;
        this.responseActive = responseActive;
        this.studentDescription = studentDescription;
        this.studentFile = studentFile;
        this.studentDate = studentDate;
        this.studentTime = studentTime;
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

    public String getTeacherRemark() {
        return teacherRemark;
    }

    public void setTeacherRemark(String teacherRemark) {
        this.teacherRemark = teacherRemark;
    }

    public String getResponseActive() {
        return responseActive;
    }

    public void setResponseActive(String responseActive) {
        this.responseActive = responseActive;
    }

    public String getStudentDescription() {
        return studentDescription;
    }

    public void setStudentDescription(String studentDescription) {
        this.studentDescription = studentDescription;
    }

    public String getStudentFile() {
        return studentFile;
    }

    public void setStudentFile(String studentFile) {
        this.studentFile = studentFile;
    }

    public String getStudentDate() {
        return studentDate;
    }

    public void setStudentDate(String studentDate) {
        this.studentDate = studentDate;
    }

    public String getStudentTime() {
        return studentTime;
    }

    public void setStudentTime(String studentTime) {
        this.studentTime = studentTime;
    }
}
