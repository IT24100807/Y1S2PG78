package com.examsystem.model;

public class AutoGradedResult extends Result {
    private String gradingAlgorithm;

    public AutoGradedResult(String resultId, String studentId, String examId, double marks, String feedback, String gradingAlgorithm) {
        super(resultId, studentId, examId, marks, feedback);
        this.gradingAlgorithm = gradingAlgorithm;
    }

    public void grade() {
        // Simulate auto-grading logic (e.g., compare with answer key)
        setMarks(calculateMarks());
        setFeedback("Auto-graded based on " + gradingAlgorithm);
    }

    private double calculateMarks() {
        // Placeholder for actual grading logic
        return getMarks(); // Simplified for demo
    }
}
