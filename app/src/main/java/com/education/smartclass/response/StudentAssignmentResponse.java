package com.education.smartclass.response;

import com.education.smartclass.models.StudentAssignmentSubmissionDetails;

public class StudentAssignmentResponse {

    private String message;
    private StudentAssignmentSubmissionDetails data;

    public StudentAssignmentResponse() {
    }

    public StudentAssignmentResponse(String message, StudentAssignmentSubmissionDetails data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StudentAssignmentSubmissionDetails getData() {
        return data;
    }

    public void setData(StudentAssignmentSubmissionDetails data) {
        this.data = data;
    }
}
