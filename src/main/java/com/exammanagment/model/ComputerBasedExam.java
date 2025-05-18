package com.exammanagment.model;

import java.time.LocalDateTime;

public class ComputerBasedExam extends Exam {
    private String softwareRequirements;

    public ComputerBasedExam() {
        super();
    }

    public ComputerBasedExam(Long id, String title, String duration, String subject, String syllabus,
                             LocalDateTime examDateTime, String status, String softwareRequirements) {
        super(id, title, duration, subject, syllabus, examDateTime, status);
        this.softwareRequirements = softwareRequirements;
    }

    @Override
    public String getExamType() {
        return "Computer-Based";
    }

    public String getSoftwareRequirements() {
        return softwareRequirements;
    }

    public void setSoftwareRequirements(String softwareRequirements) {
        this.softwareRequirements = softwareRequirements;
    }
}