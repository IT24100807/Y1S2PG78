package com.exam.result.model;

public class ManualGradedResult extends Result {
    // Inheritance - extends Result
    private String teacherFeedback;
    private String comments;

    public String getTeacherFeedback() { return teacherFeedback; }
    public void setTeacherFeedback(String teacherFeedback) { this.teacherFeedback = teacherFeedback; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    // Polymorphism - overriding parent method
    @Override
    public String generateFeedback() {
        return "Teacher's feedback: " + teacherFeedback + ". Additional comments: " + comments;
    }
}
