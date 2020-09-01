package com.education.smartclass.models;

public class ReadStudentScheduleDetails {

    private String scheduleId;
    private String topicScheduled;
    private String subjectScheduled;
    private String studentALL;
    private String scheduleDate;
    private String scheduleTime;
    private String studentCount;
    private String teacherName;
    private String scheduleDescription;

    public ReadStudentScheduleDetails() {
    }

    public ReadStudentScheduleDetails(String scheduleId, String topicScheduled, String subjectScheduled, String studentALL, String scheduleDate, String scheduleTime, String studentCount, String teacherName, String scheduleDescription) {
        this.scheduleId = scheduleId;
        this.topicScheduled = topicScheduled;
        this.subjectScheduled = subjectScheduled;
        this.studentALL = studentALL;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.studentCount = studentCount;
        this.teacherName = teacherName;
        this.scheduleDescription = scheduleDescription;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTopicScheduled() {
        return topicScheduled;
    }

    public void setTopicScheduled(String topicScheduled) {
        this.topicScheduled = topicScheduled;
    }

    public String getSubjectScheduled() {
        return subjectScheduled;
    }

    public void setSubjectScheduled(String subjectScheduled) {
        this.subjectScheduled = subjectScheduled;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public String getScheduleDescription() {
        return scheduleDescription;
    }

    public void setScheduleDescription(String scheduleDescription) {
        this.scheduleDescription = scheduleDescription;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStudentALL() {
        return studentALL;
    }

    public void setStudentALL(String studentALL) {
        this.studentALL = studentALL;
    }
}
