package com.education.smartclass.models;

public class ReadScheduleDetails {

    private String scheduleId;
    private String scheduledClass;
    private String scheduledSection;
    private String topicScheduled;
    private String subjectScheduled;
    private String scheduleDate;
    private String scheduleTime;
    private String studentCount;

    public ReadScheduleDetails() {
    }

    public ReadScheduleDetails(String scheduleId, String scheduledClass, String scheduledSection, String topicScheduled, String subjectScheduled, String scheduleDate, String scheduleTime, String studentCount) {
        this.scheduleId = scheduleId;
        this.scheduledClass = scheduledClass;
        this.scheduledSection = scheduledSection;
        this.topicScheduled = topicScheduled;
        this.subjectScheduled = subjectScheduled;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.studentCount = studentCount;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduledClass() {
        return scheduledClass;
    }

    public void setScheduledClass(String scheduledClass) {
        this.scheduledClass = scheduledClass;
    }

    public String getScheduledSection() {
        return scheduledSection;
    }

    public void setScheduledSection(String scheduledSection) {
        this.scheduledSection = scheduledSection;
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
