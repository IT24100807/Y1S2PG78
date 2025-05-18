package com.exammanagment.controller;

import com.exammanagment.model.Exam;
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

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("exam", new Exam());
        return "exam/form";
    }

    @PostMapping("/save")
    public String saveExam(@ModelAttribute Exam exam,
                           @RequestParam("examDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime) {
        exam.setExamDateTime(dateTime);
        examService.saveExam(exam);
        return "redirect:/exams";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Exam> exam = examService.getExamById(id);
        if (exam.isPresent()) {
            model.addAttribute("exam", exam.get());
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
