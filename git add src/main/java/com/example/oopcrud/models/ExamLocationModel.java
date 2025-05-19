package com.example.oopcrud.models;

public class ExamLocationModel extends BaseEntity {
    private String name;
    private String examLocation;
    private String examType; // online, physical, open book
    private String examTime;

    public ExamLocationModel() {

        super();
    }

    public ExamLocationModel(String name, String examLocation, String examType, String examTime) {
        super();
        this.name = name;
        this.examLocation = examLocation;
        this.examType = examType;
        this.examTime = examTime;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getExamLocation() {

        return examLocation;
    }

    public void setExamLocation(String examLocation) {

        this.examLocation = examLocation;
    }

    public String getExamType() {

        return examType;
    }

    public void setExamType(String examType) {

        this.examType = examType;
    }

    public String getExamTime() {

        return examTime;
    }

    public void setExamTime(String examTime) {

        this.examTime = examTime;
    }
}
