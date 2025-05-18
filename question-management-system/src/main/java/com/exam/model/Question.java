package com.exam.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.Data;

@Data
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "questionType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MCQQuestion.class, name = "MCQ"),
        @JsonSubTypes.Type(value = TrueFalseQuestion.class, name = "TRUE_FALSE"),
        @JsonSubTypes.Type(value = EssayQuestion.class, name = "ESSAY")
})
public abstract class Question {
    private Long id;
    private String questionText;
    private Integer marks;
    private String difficulty;
    private String topic;
    private String examId;
    private String questionType;

    public abstract String renderQuestion();

    public String getQuestionType() {
        return this.questionType != null ? this.questionType :
                this.getClass().getSimpleName().replace("Question", "").toUpperCase();
    }


}