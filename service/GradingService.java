package com.exam.result.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradingService {
    public String calculateGrade(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    public double calculateClassAverage(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double score : scores) {
            sum += score;
        }

        return sum / scores.size();
    }

    public String calculateClassPerformance(double averageScore) {
        if (averageScore >= 80) return "Excellent";
        if (averageScore >= 70) return "Good";
        if (averageScore >= 60) return "Average";
        return "Needs Improvement";
    }
}