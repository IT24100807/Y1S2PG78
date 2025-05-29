package com.exam.result.controller;

import com.exam.result.model.Result;
import com.exam.result.model.StudentResultNode;
import com.exam.result.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultService resultService;

    // CRUD endpoints
    @PostMapping
    public String createResult(@ModelAttribute Result result) {
        resultService.createResult(result);
        return "redirect:/results";
    }

    @GetMapping
    public String getAllResults(Model model) {
        List<Result> results = resultService.getAllResults();
        model.addAttribute("results", results);
        return "result-dashboard";
    }

    @GetMapping("/exam/{examId}")
    public String getResultsByExam(@PathVariable String examId, Model model) {
        List<Result> results = resultService.getResultsByExam(examId);
        model.addAttribute("results", results);
        return "result-dashboard";
    }

    @GetMapping("/student/{studentId}")
    public String getResultsByStudent(@PathVariable String studentId, Model model) {
        List<Result> results = resultService.getResultsByStudent(studentId);
        model.addAttribute("results", results);
        return "result-dashboard";
    }

    @PutMapping("/{id}")
    public String updateResult(@PathVariable String id, @ModelAttribute Result result) {
        result.setId(id);
        resultService.updateResult(result);
        return "redirect:/results";
    }

    @DeleteMapping("/{id}")
    public String deleteResult(@PathVariable String id) {
        resultService.deleteResult(id);
        return "redirect:/results";
    }

    // Additional features
    @GetMapping("/sorted")
    public String getSortedResults(Model model) {
        List<Result> results = resultService.getAllResults();
        // Algorithm - Using Selection Sort to sort by score
        List<Result> sortedResults = resultService.sortResultsByScore(results);
        model.addAttribute("results", sortedResults);
        return "result-dashboard";
    }

    @GetMapping("/linked")
    public String getResultsAsLinkedList(Model model) {
        List<Result> results = resultService.getAllResults();
        // Data Structure - Using Linked Sort to maintain records
        StudentResultNode head = resultService.createResultLinkedList(results);

        // Convert linked list back to list for display
        List<Result> linkedResults = new ArrayList<>();
        StudentResultNode current = head;
        while (current != null) {
            linkedResults.add(current.getResult());
            current = current.getNext();
        }

        model.addAttribute("results", linkedResults);
        return "result-dashboard";
    }

    @GetMapping("/gradebook")
    public String getGradebook(Model model) {
        List<Result> results = resultService.getAllResults();
        model.addAttribute("results", results);
        return "gradebook";
    }

    @GetMapping("/analytics")
    public String getAnalytics(Model model) {
        List<Result> results = resultService.getAllResults();
        model.addAttribute("results", results);
        return "result-analytics";
    }
}