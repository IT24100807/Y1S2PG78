package com.exam.service;

import com.exam.model.EssayQuestion;
import com.exam.model.MCQQuestion;
import com.exam.model.Question;
import com.exam.model.TrueFalseQuestion;
import com.exam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }
    
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }
    
    public Question updateQuestion(Long id, Question questionDetails) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found"));
            
        question.setQuestionText(questionDetails.getQuestionText());
        question.setMarks(questionDetails.getMarks());
        question.setDifficulty(questionDetails.getDifficulty());
        question.setTopic(questionDetails.getTopic());
        question.setExamId(questionDetails.getExamId());
        
        return questionRepository.save(question);
    }
    
    public Question updateQuestion(Question question) {
        Question existingQuestion = getQuestionById(question.getId())
            .orElseThrow(() -> new RuntimeException("Question not found"));
        
        // Update common fields
        existingQuestion.setQuestionText(question.getQuestionText());
        existingQuestion.setTopic(question.getTopic());
        existingQuestion.setMarks(question.getMarks());
        existingQuestion.setDifficulty(question.getDifficulty());
        existingQuestion.setExamId(question.getExamId());

        // Update type-specific fields
        if (question instanceof MCQQuestion && existingQuestion instanceof MCQQuestion) {
            MCQQuestion mcqQuestion = (MCQQuestion) question;
            MCQQuestion existingMcq = (MCQQuestion) existingQuestion;
            existingMcq.setOptions(mcqQuestion.getOptions());
            existingMcq.setCorrectAnswer(mcqQuestion.getCorrectAnswer());
        } else if (question instanceof TrueFalseQuestion && existingQuestion instanceof TrueFalseQuestion) {
            TrueFalseQuestion tfQuestion = (TrueFalseQuestion) question;
            TrueFalseQuestion existingTf = (TrueFalseQuestion) existingQuestion;
            existingTf.setCorrectAnswer(tfQuestion.getCorrectAnswer());
        } else if (question instanceof EssayQuestion && existingQuestion instanceof EssayQuestion) {
            EssayQuestion essayQuestion = (EssayQuestion) question;
            EssayQuestion existingEssay = (EssayQuestion) existingQuestion;
            existingEssay.setWordLimit(essayQuestion.getWordLimit());
            existingEssay.setSampleAnswer(essayQuestion.getSampleAnswer());
        }

        return questionRepository.save(existingQuestion);
    }
    
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found"));
        questionRepository.delete(question);
    }
    
    public List<Question> searchQuestions(String topic, String type, String difficulty) {
        List<Question> questions = getAllQuestions();
        
        return questions.stream()
            .filter(q -> topic == null || q.getTopic().toLowerCase().contains(topic.toLowerCase()))
            .filter(q -> type == null || q.getClass().getSimpleName().replace("Question", "").equals(type))
            .filter(q -> difficulty == null || q.getDifficulty().equals(difficulty))
            .collect(Collectors.toList());
    }
    
    public List<Question> bulkUpload(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        
        // Process each row and create questions
        // Implementation depends on the Excel structure
        // This is a placeholder for the actual implementation
        
        workbook.close();
        return getAllQuestions();
    }
}
