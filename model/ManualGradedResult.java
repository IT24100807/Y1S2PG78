package com.examsystem.model;

public class ManualGradedResult extends Result {
    private String teacherId;

    public ManualGradedResult(String resultId, String studentId, String examId, double marks, String feedback, String teacherId) {
        super(resultId, studentId, examId, marks, feedback);
        this.teacherId = teacherId;
    }

    public void grade() {
        // Simulate manual grading
        setFeedback("Graded by teacher: " + teacherId);
    }
}