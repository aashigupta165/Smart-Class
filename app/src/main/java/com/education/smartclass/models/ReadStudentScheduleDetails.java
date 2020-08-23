package com.education.smartclass.models;

public class ReadStudentScheduleDetails {
    
    private String topicScheduled;
    private String subjectScheduled;
    private String scheduleDate;
    private String scheduleTime;
    private String studentCount;
    private String teacherName;

    public ReadStudentScheduleDetails() {
    }

    public ReadStudentScheduleDetails(String topicScheduled, String subjectScheduled, String scheduleDate, String scheduleTime, String studentCount, String teacherName) {
        this.topicScheduled = topicScheduled;
        this.subjectScheduled = subjectScheduled;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.studentCount = studentCount;
        this.teacherName = teacherName;
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
}
