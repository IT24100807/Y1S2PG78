package com.exam.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrueFalseQuestion extends Question {
    private Boolean correctAnswer;

    public TrueFalseQuestion() {
        this.setQuestionType("TRUE_FALSE");
    }

    @Override
    public String renderQuestion() {
        return getQuestionText() + "\nTrue or False?";
    }
}