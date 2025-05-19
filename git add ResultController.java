package com.examsystem.controller;

import com.examsystem.model.Result;
import com.examsystem.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResponseEntity<String> createResult(
            @RequestParam String studentId,
            @RequestParam String examId,
            @RequestParam double marks,
            @RequestParam String feedback,
            @RequestParam String gradingType,
            @RequestParam(required = false) String teacherId) throws IOException {
        resultService.createResult(studentId, examId, marks, feedback, gradingType, teacherId);
        return ResponseEntity.ok("Result created successfully");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getResultsByStudent(@PathVariable String studentId) throws IOException {
        return ResponseEntity.ok(resultService.getResultsByStudent(studentId));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<Result>> getResultsByExam(@PathVariable String examId) throws IOException {
        return ResponseEntity.ok(resultService.getResultsByExam(examId));
    }

    @PutMapping("/{resultId}")
    public ResponseEntity<String> updateResult(
            @PathVariable String resultId,
            @RequestParam double marks,
            @RequestParam String feedback) throws IOException {
        resultService.updateResult(resultId, marks, feedback);
        return ResponseEntity.ok("Result updated successfully");
    }

    @DeleteMapping("/{resultId}")
    public ResponseEntity<String> deleteResult(@PathVariable String resultId) throws IOException {
        resultService.deleteResult(resultId);
        return ResponseEntity.ok("Result deleted successfully");
    }
}

