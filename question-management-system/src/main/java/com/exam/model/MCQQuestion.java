package com.exam.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MCQQuestion extends Question {
    private List<String> options;
    private String correctAnswer;

    public MCQQuestion() {
        this.setQuestionType("MCQ");
    }

    @Override
    public String renderQuestion() {
        StringBuilder sb = new StringBuilder(getQuestionText());
        sb.append("\nOptions:\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append((char)('A' + i)).append(") ").append(options.get(i)).append("\n");
        }
        return sb.toString();
    }
}