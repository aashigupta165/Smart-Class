package com.education.smartclass.models;

public class AssignmentDetailsList {
    private String assignmentId;
    private String assignmentTitle;
    private String classAssignment;
    private String sectionAssignment;
    private String subjectAssignment;
    private String teacherRemark;
    private String assignmentDate;
    private String assignmentTime;
    private String description;
    private String file;
    private String submitCount;
    private String active;

    public AssignmentDetailsList() {
    }

    public AssignmentDetailsList(String assignmentId, String assignmentTitle, String classAssignment, String sectionAssignment, String subjectAssignment, String teacherRemark, String assignmentDate, String assignmentTime, String description, String file, String submitCount, String active) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.classAssignment = classAssignment;
        this.sectionAssignment = sectionAssignment;
        this.subjectAssignment = subjectAssignment;
        this.teacherRemark = teacherRemark;
        this.assignmentDate = assignmentDate;
        this.assignmentTime = assignmentTime;
        this.description = description;
        this.file = file;
        this.submitCount = submitCount;
        this.active = active;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getClassAssignment() {
        return classAssignment;
    }

    public void setClassAssignment(String classAssignment) {
        this.classAssignment = classAssignment;
    }

    public String getSectionAssignment() {
        return sectionAssignment;
    }

    public void setSectionAssignment(String sectionAssignment) {
        this.sectionAssignment = sectionAssignment;
    }

    public String getSubjectAssignment() {
        return subjectAssignment;
    }

    public void setSubjectAssignment(String subjectAssignment) {
        this.subjectAssignment = subjectAssignment;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getAssignmentTime() {
        return assignmentTime;
    }

    public void setAssignmentTime(String assignmentTime) {
        this.assignmentTime = assignmentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(String submitCount) {
        this.submitCount = submitCount;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTeacherRemark() {
        return teacherRemark;
    }

    public void setTeacherRemark(String teacherRemark) {
        this.teacherRemark = teacherRemark;
    }
}
