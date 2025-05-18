package com.exam.controller;

import com.exam.model.Question;
import com.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        question.setId(id);
        Question updatedQuestion = questionService.updateQuestion(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> searchQuestions(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String difficulty) {
        return ResponseEntity.ok(questionService.searchQuestions(topic, type, difficulty));
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<List<Question>> bulkUpload(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(questionService.bulkUpload(file));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
