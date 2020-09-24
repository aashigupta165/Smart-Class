package com.education.smartclass.models;

public class StudentAssignmentSubmissionDetails {

    private String teacherRemark;
    private String responseActive;
    private String studentDescription;
    private String studentFile;
    private String submitDate;
    private String submitTime;

    public StudentAssignmentSubmissionDetails() {
    }

    public StudentAssignmentSubmissionDetails(String teacherRemark, String responseActive, String studentDescription, String studentFile, String submitDate, String submitTime) {
        this.teacherRemark = teacherRemark;
        this.responseActive = responseActive;
        this.studentDescription = studentDescription;
        this.studentFile = studentFile;
        this.submitDate = submitDate;
        this.submitTime = submitTime;
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

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
