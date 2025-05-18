package com.exam.repository;

import com.exam.model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class QuestionRepository {
    private static final String FILE_PATH = "questions.txt";
    private final ObjectMapper objectMapper;
    private final File dataFile;

    public QuestionRepository() {
        this.objectMapper = new ObjectMapper();
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.exam.model")
                .build();
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        this.dataFile = new File(FILE_PATH);
        ensureFileExists();
    }

    private void ensureFileExists() {
        if (!dataFile.exists()) {
            try {
                File parent = dataFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create questions data file", e);
            }
        }
    }

    public List<Question> findAll() {
        try {
            ensureFileExists();

            try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
                return reader.lines()
                        .map(line -> {
                            try {
                                return objectMapper.readValue(line, Question.class);
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to parse question: " + line, e);
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read questions from file", e);
        }
    }

    public Optional<Question> findById(Long id) {
        return findAll().stream()
                .filter(q -> q.getId().equals(id))
                .findFirst();
    }

    public Question save(Question question) {
        List<Question> questions = findAll();

        if (question.getId() == null) {
            // New question - generate ID
            long newId = questions.stream()
                    .mapToLong(Question::getId)
                    .max()
                    .orElse(0) + 1;
            question.setId(newId);
        } else {
            // Existing question - remove old version
            questions.removeIf(q -> q.getId().equals(question.getId()));
        }

        questions.add(question);
        writeAll(questions);
        return question;
    }

    public void delete(Question question) {
        List<Question> questions = findAll();
        questions.removeIf(q -> q.getId().equals(question.getId()));
        writeAll(questions);
    }

    private void writeAll(List<Question> questions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            for (Question question : questions) {
                String json = objectMapper.writeValueAsString(question);
                writer.println(json);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write questions to file", e);
        }
    }
}