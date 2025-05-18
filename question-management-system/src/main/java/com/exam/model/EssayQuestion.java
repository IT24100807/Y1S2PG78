package com.exam.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EssayQuestion extends Question {
    private Integer wordLimit;
    private String sampleAnswer;

    public EssayQuestion() {
        this.setQuestionType("ESSAY");
    }

    @Override
    public String renderQuestion() {
        return getQuestionText() + "\nWord limit: " + wordLimit;
    }
}