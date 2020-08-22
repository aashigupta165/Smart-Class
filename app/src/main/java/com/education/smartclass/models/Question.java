package com.education.smartclass.models;

public class Question {

    private String questionId;
    private String purposeOfQuestion;
    private String subject;
    private String question;
    private String questionAskerName;
    private String questionAskerRole;
    private String questionAskerCode;
    private String questionForClass;
    private String questionForSection;
    private String questionDateTime;

    public Question() {
    }

    public Question(String questionId, String purposeOfQuestion, String subject, String question, String questionAskerName, String questionAskerRole, String questionAskerCode, String questionForClass, String questionForSection, String questionDateTime) {
        this.questionId = questionId;
        this.purposeOfQuestion = purposeOfQuestion;
        this.subject = subject;
        this.question = question;
        this.questionAskerName = questionAskerName;
        this.questionAskerRole = questionAskerRole;
        this.questionAskerCode = questionAskerCode;
        this.questionForClass = questionForClass;
        this.questionForSection = questionForSection;
        this.questionDateTime = questionDateTime;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getPurposeOfQuestion() {
        return purposeOfQuestion;
    }

    public void setPurposeOfQuestion(String purposeOfQuestion) {
        this.purposeOfQuestion = purposeOfQuestion;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionAskerName() {
        return questionAskerName;
    }

    public void setQuestionAskerName(String questionAskerName) {
        this.questionAskerName = questionAskerName;
    }

    public String getQuestionAskerRole() {
        return questionAskerRole;
    }

    public void setQuestionAskerRole(String questionAskerRole) {
        this.questionAskerRole = questionAskerRole;
    }

    public String getQuestionAskerCode() {
        return questionAskerCode;
    }

    public void setQuestionAskerCode(String questionAskerCode) {
        this.questionAskerCode = questionAskerCode;
    }

    public String getQuestionForClass() {
        return questionForClass;
    }

    public void setQuestionForClass(String questionForClass) {
        this.questionForClass = questionForClass;
    }

    public String getQuestionForSection() {
        return questionForSection;
    }

    public void setQuestionForSection(String questionForSection) {
        this.questionForSection = questionForSection;
    }

    public String getQuestionDateTime() {
        return questionDateTime;
    }

    public void setQuestionDateTime(String questionDateTime) {
        this.questionDateTime = questionDateTime;
    }
}
