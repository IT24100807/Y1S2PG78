package com.exam.result.service;

import com.exam.result.model.Result;
import com.exam.result.model.StudentResultNode;
import com.exam.result.repository.ResultRepository;
import com.exam.result.util.ResultSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private GradingService gradingService;

    // CRUD operations
    public void createResult(Result result) {
        result.setGrade(gradingService.calculateGrade(result.getScore()));
        resultRepository.createResult(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.readAllResults();
    }

    public List<Result> getResultsByExam(String examId) {
        return resultRepository.readResultsByExam(examId);
    }

    public List<Result> getResultsByStudent(String studentId) {
        return resultRepository.readResultsByStudent(studentId);
    }

    public void updateResult(Result result) {
        result.setGrade(gradingService.calculateGrade(result.getScore()));
        resultRepository.updateResult(result);
    }

    public void deleteResult(String resultId) {
        resultRepository.deleteResult(resultId);
    }

    // Data Structure - Linked Sort implementation to maintain student records dynamically
    public StudentResultNode createResultLinkedList(List<Result> results) {
        if (results.isEmpty()) {
            return null;
        }

        StudentResultNode head = new StudentResultNode(results.get(0));
        StudentResultNode current = head;

        for (int i = 1; i < results.size(); i++) {
            StudentResultNode newNode = new StudentResultNode(results.get(i));
            current.setNext(newNode);
            current = newNode;
        }

        return head;
    }

    // Algorithm - Selection Sort to sort students by scores
    public List<Result> sortResultsByScore(List<Result> results) {
        return ResultSorter.selectionSortByScore(results);
    }
}
