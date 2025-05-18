package com.exammanagment.repository;

import com.exammanagment.model.*;
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
        StringBuilder sb = new StringBuilder();
        sb.append(exam.getId()).append("|")
                .append(exam.getTitle()).append("|")
                .append(exam.getDuration()).append("|")
                .append(exam.getSubject()).append("|")
                .append(exam.getSyllabus()).append("|")
                .append(exam.getExamDateTime().format(FORMATTER)).append("|")
                .append(exam.getStatus()).append("|")
                .append(exam.getExamType());

        if (exam instanceof ComputerBasedExam) {
            ComputerBasedExam cbe = (ComputerBasedExam) exam;
            sb.append("|").append(cbe.getSoftwareRequirements());
        } else if (exam instanceof OnPaperExam) {
            OnPaperExam ope = (OnPaperExam) exam;
            sb.append("|").append(ope.getPaperType())
                    .append("|").append(ope.isRequiresAnswerSheet());
        }

        return sb.toString();
    }

    private Exam fromLine(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length < 8) return null;

        Long id = Long.parseLong(parts[0]);
        String title = parts[1];
        String duration = parts[2];
        String subject = parts[3];
        String syllabus = parts[4];
        LocalDateTime examDateTime = LocalDateTime.parse(parts[5], FORMATTER);
        String status = parts[6];
        String examType = parts[7];

        if ("Computer-Based".equals(examType) && parts.length >= 9) {
            ComputerBasedExam exam = new ComputerBasedExam();
            exam.setId(id);
            exam.setTitle(title);
            exam.setDuration(duration);
            exam.setSubject(subject);
            exam.setSyllabus(syllabus);
            exam.setExamDateTime(examDateTime);
            exam.setStatus(status);
            exam.setSoftwareRequirements(parts[8]);
            return exam;
        } else if ("On-Paper".equals(examType) && parts.length >= 10) {
            OnPaperExam exam = new OnPaperExam();
            exam.setId(id);
            exam.setTitle(title);
            exam.setDuration(duration);
            exam.setSubject(subject);
            exam.setSyllabus(syllabus);
            exam.setExamDateTime(examDateTime);
            exam.setStatus(status);
            exam.setPaperType(parts[8]);
            exam.setRequiresAnswerSheet(Boolean.parseBoolean(parts[9]));
            return exam;
        }

        return null;
    }

    // Rest of the methods remain the same as they work with Exam interface
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