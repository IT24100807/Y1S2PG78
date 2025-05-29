package com.exam.result.model;

public class AutoGradedResult extends Result {
    // Inheritance - extends Result
    private String autoFeedback;

    public String getAutoFeedback() { return autoFeedback; }
    public void setAutoFeedback(String autoFeedback) { this.autoFeedback = autoFeedback; }

    // Polymorphism - overriding parent method
    @Override
    public String generateFeedback() {
        return "Automatically generated feedback based on performance. " + autoFeedback;
    }
}
