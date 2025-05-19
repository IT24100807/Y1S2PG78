package com.examsystem.service;

import com.examsystem.model.Result;
import com.examsystem.model.AutoGradedResult;
import com.examsystem.model.ManualGradedResult;
import com.examsystem.util.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ResultService {

    public void createResult(String studentId, String examId, double marks, String feedback, String gradingType, String teacherId) throws IOException {
        String resultId = UUID.randomUUID().toString();
        Result result;
        if ("auto".equalsIgnoreCase(gradingType)) {
            result = new AutoGradedResult(resultId, studentId, examId, marks, feedback, "MCQAlgorithm");
            ((AutoGradedResult) result).grade();
        } else {
            result = new ManualGradedResult(resultId, studentId, examId, marks, feedback, teacherId);
            ((ManualGradedResult) result).grade();
        }
        FileUtil.writeResult(result);
    }

    public List<Result> getResultsByStudent(String studentId) throws IOException {
        return FileUtil.readResults().stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Result> getResultsByExam(String examId) throws IOException {
        return FileUtil.readResults().stream()
                .filter(r -> r.getExamId().equals(examId))
                .collect(Collectors.toList());
    }

    public void updateResult(String resultId, double newMarks, String newFeedback) throws IOException {
        List<Result> results = FileUtil.readResults();
        Result result = results.stream()
                .filter(r -> r.getResultId().equals(resultId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Result not found"));
        result.setMarks(newMarks);
        result.setFeedback(newFeedback);
        FileUtil.updateResult(result);
    }

    public void deleteResult(String resultId) throws IOException {
        FileUtil.deleteResult(resultId);
    }
}