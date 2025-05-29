package com.exam.result.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> readAllLines(String filename) {
        try {
            Resource resource = new ClassPathResource("static/" + filename);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void writeAllLines(String filename, List<String> lines) {
        try {
            Resource resource = new ClassPathResource("static/" + filename);
            String filePath = resource.getFile().getAbsolutePath();

            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void appendToFile(String filename, String line) {
        try {
            Resource resource = new ClassPathResource("static/" + filename);
            String filePath = resource.getFile().getAbsolutePath();

            Files.write(Paths.get(filePath), (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }
}
