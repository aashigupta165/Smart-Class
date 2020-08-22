package com.education.smartclass.models;

public class Reply {

    private String replyId;
    private String reply;
    private String replyDateTime;
    private String replierName;
    private String replierRole;
    private String replierCode;
    private String replierClass;
    private String replierSection;

    public Reply() {
    }

    public Reply(String replyId, String reply, String replyDateTime, String replierName, String replierRole, String replierCode, String replierClass, String replierSection) {
        this.replyId = replyId;
        this.reply = reply;
        this.replyDateTime = replyDateTime;
        this.replierName = replierName;
        this.replierRole = replierRole;
        this.replierCode = replierCode;
        this.replierClass = replierClass;
        this.replierSection = replierSection;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyDateTime() {
        return replyDateTime;
    }

    public void setReplyDateTime(String replyDateTime) {
        this.replyDateTime = replyDateTime;
    }

    public String getReplierName() {
        return replierName;
    }

    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }

    public String getReplierRole() {
        return replierRole;
    }

    public void setReplierRole(String replierRole) {
        this.replierRole = replierRole;
    }

    public String getReplierCode() {
        return replierCode;
    }

    public void setReplierCode(String replierCode) {
        this.replierCode = replierCode;
    }

    public String getReplierClass() {
        return replierClass;
    }

    public void setReplierClass(String replierClass) {
        this.replierClass = replierClass;
    }

    public String getReplierSection() {
        return replierSection;
    }

    public void setReplierSection(String replierSection) {
        this.replierSection = replierSection;
    }
}
