package com.examsystem.model;

public class Result {
    private String resultId;
    private String studentId;
    private String examId;
    private double marks;
    private String feedback;

    public Result(String resultId, String studentId, String examId, double marks, String feedback) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.examId = examId;
        this.marks = marks;
        this.feedback = feedback;
    }

    // Getters and Setters
    public String getResultId() { return resultId; }
    public void setResultId(String resultId) { this.resultId = resultId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    @Override
    public String toString() {
        return resultId + "," + studentId + "," + examId + "," + marks + "," + feedback;
    }
}