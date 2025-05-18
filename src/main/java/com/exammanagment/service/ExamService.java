package com.exammanagment.service;

import com.exammanagment.model.Exam;
import com.exammanagment.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    @Autowired
    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public void saveExam(Exam exam) {
        examRepository.saveExam(exam);
    }

    public List<Exam> getAllExams() {
        return examRepository.getAllExams();
    }

    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    public boolean deleteExam(Long id) {
        return examRepository.deleteExam(id);
    }
}
