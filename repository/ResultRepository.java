package com.exam.result.repository;

import com.exam.result.model.Result;
import com.exam.result.util.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResultRepository {
    private static final String RESULTS_FILE = "results.txt";

    // CRUD Operations
    public void createResult(Result result) {
        FileUtil.appendToFile(RESULTS_FILE, resultToString(result));
    }

    public List<Result> readAllResults() {
        List<String> lines = FileUtil.readAllLines(RESULTS_FILE);
        List<Result> results = new ArrayList<>();

        for (String line : lines) {
            results.add(stringToResult(line));
        }
        return results;
    }

    public List<Result> readResultsByExam(String examId) {
        List<Result> allResults = readAllResults();
        List<Result> filteredResults = new ArrayList<>();

        for (Result result : allResults) {
            if (result.getExamId().equals(examId)) {
                filteredResults.add(result);
            }
        }
        return filteredResults;
    }

    public List<Result> readResultsByStudent(String studentId) {
        List<Result> allResults = readAllResults();
        List<Result> filteredResults = new ArrayList<>();

        for (Result result : allResults) {
            if (result.getStudentId().equals(studentId)) {
                filteredResults.add(result);
            }
        }
        return filteredResults;
    }

    public void updateResult(Result updatedResult) {
        List<Result> allResults = readAllResults();
        List<String> updatedLines = new ArrayList<>();

        for (Result result : allResults) {
            if (result.getId().equals(updatedResult.getId())) {
                updatedLines.add(resultToString(updatedResult));
            } else {
                updatedLines.add(resultToString(result));
            }
        }

        FileUtil.writeAllLines(RESULTS_FILE, updatedLines);
    }

    public void deleteResult(String resultId) {
        List<Result> allResults = readAllResults();
        List<String> updatedLines = new ArrayList<>();

        for (Result result : allResults) {
            if (!result.getId().equals(resultId)) {
                updatedLines.add(resultToString(result));
            }
        }

        FileUtil.writeAllLines(RESULTS_FILE, updatedLines);
    }

    private String resultToString(Result result) {
        return String.format("%s,%s,%s,%.2f,%s",
                result.getId(),
                result.getExamId(),
                result.getStudentId(),
                result.getScore(),
                result.getGrade());
    }

    private Result stringToResult(String line) {
        String[] parts = line.split(",");
        Result result = new Result();
        result.setId(parts[0]);
        result.setExamId(parts[1]);
        result.setStudentId(parts[2]);
        result.setScore(Double.parseDouble(parts[3]));
        result.setGrade(parts[4]);
        return result;
    }
}
