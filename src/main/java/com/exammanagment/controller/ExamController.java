package com.exammanagment.controller;

import com.exammanagment.model.*;
import com.exammanagment.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    public String viewExams(Model model) {
        model.addAttribute("exams", examService.getAllExams());
        return "exam/list";
    }

    @GetMapping("/home")
    public String examHome() {
        return "exam/home";
    }

    @GetMapping("/new-computer")
    public String showCreateComputerForm(Model model) {
        model.addAttribute("exam", new ComputerBasedExam());
        model.addAttribute("examType", "computer");
        return "exam/form";
    }

    @GetMapping("/new-paper")
    public String showCreatePaperForm(Model model) {
        model.addAttribute("exam", new OnPaperExam());
        model.addAttribute("examType", "paper");
        return "exam/form";
    }

    @PostMapping("/save-computer")
    public String saveComputerExam(@ModelAttribute ComputerBasedExam exam,
                                   @RequestParam("examDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime) {
        exam.setExamDateTime(dateTime);
        examService.saveExam(exam);
        return "redirect:/exams";
    }

    @PostMapping("/save-paper")
    public String savePaperExam(@ModelAttribute OnPaperExam exam,
                                @RequestParam("examDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime) {
        exam.setExamDateTime(dateTime);
        examService.saveExam(exam);
        return "redirect:/exams";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Exam> exam = examService.getExamById(id);
        if (exam.isPresent()) {
            Exam existingExam = exam.get();
            model.addAttribute("exam", existingExam);
            model.addAttribute("examType", existingExam instanceof ComputerBasedExam ? "computer" : "paper");
            return "exam/form";
        } else {
            return "redirect:/exams";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return "redirect:/exams";
    }
}