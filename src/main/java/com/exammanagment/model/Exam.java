package com.exammanagment.model;

import java.time.LocalDateTime;

public abstract class Exam {
    private Long id;
    private String title;
    private String duration;
    private String subject;
    private String syllabus;
    private LocalDateTime examDateTime;
    private String status;

    public Exam() {}

    public Exam(Long id, String title, String duration, String subject, String syllabus, LocalDateTime examDateTime, String status) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.subject = subject;
        this.syllabus = syllabus;
        this.examDateTime = examDateTime;
        this.status = status;
    }

    // Abstract method to get exam type
    public abstract String getExamType();

    // Getters and Setters (same as before)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public LocalDateTime getExamDateTime() {
        return examDateTime;
    }

    public void setExamDateTime(LocalDateTime examDateTime) {
        this.examDateTime = examDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}