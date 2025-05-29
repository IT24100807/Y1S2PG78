package com.exam.result.model;

public class Result {
    private String id;
    private String examId;
    private String studentId;
    private double score;
    private String grade;

    // Encapsulation with getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    // Polymorphism - method to be overridden by child classes
    public String generateFeedback() {
        return "General feedback for the result";
    }
}