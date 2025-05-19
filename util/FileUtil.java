package com.examsystem.util;

import com.examsystem.model.Result;
import com.examsystem.model.AutoGradedResult;
import com.examsystem.model.ManualGradedResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtil {
    private static final String FILE_PATH = "src/main/resources/data/results.txt";

    public static void writeResult(Result result) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(result.toString());
            writer.newLine();
        }
    }

    public static List<Result> readResults() throws IOException {
        List<Result> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Result result = new Result(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
                    results.add(result);
                }
            }
        }
        return results;
    }

    public static void updateResult(Result updatedResult) throws IOException {
        List<Result> results = readResults();
        results.removeIf(r -> r.getResultId().equals(updatedResult.getResultId()));
        results.add(updatedResult);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Result result : results) {
                writer.write(result.toString());
                writer.newLine();
            }
        }
    }

    public static void deleteResult(String resultId) throws IOException {
        List<Result> results = readResults();
        results.removeIf(r -> r.getResultId().equals(resultId));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Result result : results) {
                writer.write(result.toString());
                writer.newLine();
            }
        }
    }
}
