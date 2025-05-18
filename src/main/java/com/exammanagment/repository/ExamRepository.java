package com.exammanagment.repository;

import com.exammanagment.model.Exam;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

        @Repository
        public class ExamRepository {

            private static final String DIRECTORY = "data";
            private static final String FILE_NAME = "exams.txt";
            private static final Path FILE_PATH = Paths.get(DIRECTORY, FILE_NAME);
            private static final String DELIMITER = "\\|";
            private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            public ExamRepository() {
                initializeFile();
            }

            private void initializeFile() {
                try {
                    if (!Files.exists(FILE_PATH.getParent())) {
                        Files.createDirectories(FILE_PATH.getParent());
                    }
                    if (!Files.exists(FILE_PATH)) {
                        Files.createFile(FILE_PATH);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private String toLine(Exam exam) {
                return String.format("%d|%s|%s|%s|%s|%s|%s",
                        exam.getId(),
                        exam.getTitle(),
                        exam.getDuration(),
                        exam.getSubject(),
                        exam.getSyllabus(),
                        exam.getExamDateTime().format(FORMATTER),
                        exam.getStatus()
                );
            }

            private Exam fromLine(String line) {
                String[] parts = line.split(DELIMITER);
                if (parts.length < 7) return null;

                Exam exam = new Exam();
                exam.setId(Long.parseLong(parts[0]));
        return exam;
    }

    public void saveExam(Exam exam) {
        List<Exam> exams = getAllExams();
        boolean updated = false;

        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getId().equals(exam.getId())) {
                exams.set(i, exam);
                updated = true;
                break;
            }
        }

        if (!updated) {
            exam.setId(generateNewId(exams));
            exams.add(exam);
        }

        writeAllExamsToFile(exams);
    }

    public List<Exam> getAllExams() {
        List<Exam> exams = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Exam exam = fromLine(line);
                if (exam != null) {
                    exams.add(exam);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exams;
    }

    public Optional<Exam> findById(Long id) {
        return getAllExams().stream()
                .filter(exam -> exam.getId().equals(id))
                .findFirst();
    }

    public boolean deleteExam(Long id) {
        List<Exam> exams = getAllExams();
        boolean removed = exams.removeIf(exam -> exam.getId().equals(id));
        if (removed) {
            writeAllExamsToFile(exams);
        }
        return removed;
    }

    private Long generateNewId(List<Exam> exams) {
        return exams.stream()
                .mapToLong(Exam::getId)
                .max()
                .orElse(0L) + 1;
    }

    private void writeAllExamsToFile(List<Exam> exams) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
            for (Exam exam : exams) {
                writer.write(toLine(exam));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}