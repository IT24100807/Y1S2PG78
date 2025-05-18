package com.exammanagment.model;

import java.time.LocalDateTime;

public class OnPaperExam extends Exam {
    private String paperType;
    private boolean requiresAnswerSheet;

    public OnPaperExam() {
        super();
    }

    public OnPaperExam(Long id, String title, String duration, String subject, String syllabus,
                       LocalDateTime examDateTime, String status, String paperType, boolean requiresAnswerSheet) {
        super(id, title, duration, subject, syllabus, examDateTime, status);
        this.paperType = paperType;
        this.requiresAnswerSheet = requiresAnswerSheet;
    }

    @Override
    public String getExamType() {
        return "On-Paper";
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public boolean isRequiresAnswerSheet() {
        return requiresAnswerSheet;
    }

    public void setRequiresAnswerSheet(boolean requiresAnswerSheet) {
        this.requiresAnswerSheet = requiresAnswerSheet;
    }
}