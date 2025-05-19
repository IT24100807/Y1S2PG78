package com.example.oopcrud.services;

import com.example.oopcrud.models.ExamLocationModel;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamLocationService {

    private final File file = new File("exam_locations.txt");
    public ExamLocationService() {
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("exam_locations.txt created successfully.");
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to create file: " + e.getMessage());
        }
    }
    public List<ExamLocationModel> getAllExamLocations() {
        List<ExamLocationModel> locations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    ExamLocationModel model = new ExamLocationModel(
                            parts[0], parts[1], parts[2], parts[3]
                    );
                    model.setId(Integer.parseInt(parts[4]));
                    locations.add(model);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return locations;
    }

    public void addExamLocation(ExamLocationModel model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            model.setId(generateNewId());
            writer.write(model.getName() + "|" + model.getExamLocation() + "|" + model.getExamType() + "|" + model.getExamTime() + "|" + model.getId());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void updateExamLocation(int id, ExamLocationModel updatedModel) {
        try {
            List<ExamLocationModel> locations = getAllExamLocations();
            for (ExamLocationModel model : locations) {
                if (model.getId() == id) {
                    model.setName(updatedModel.getName());
                    model.setExamLocation(updatedModel.getExamLocation());
                    model.setExamType(updatedModel.getExamType());
                    model.setExamTime(updatedModel.getExamTime());
                }
            }
            saveAll(locations);
        } catch (Exception e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    public void deleteExamLocation(int id) {
        try {
            List<ExamLocationModel> locations = getAllExamLocations();
            List<ExamLocationModel> updated = locations.stream()
                    .filter(model -> model.getId() != id)
                    .collect(Collectors.toList());
            saveAll(updated);
        } catch (Exception e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    private void saveAll(List<ExamLocationModel> locations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (ExamLocationModel model : locations) {
                writer.write(model.getName() + "|" + model.getExamLocation() + "|" + model.getExamType() + "|" + model.getExamTime() + "|" + model.getId());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    private int generateNewId() {
        List<ExamLocationModel> existing = getAllExamLocations();
        return existing.stream()
                .mapToInt(ExamLocationModel::getId)
                .max()
                .orElse(0) + 1;
    }

    public ExamLocationModel getExamLocationById(int id) {
        try {
            List<ExamLocationModel> locations = getAllExamLocations();
            for (ExamLocationModel model : locations) {
                if (model.getId() == id) {
                    return model;
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching record by ID: " + e.getMessage());
        }
        return null; // or throw a custom exception if preferred
    }

}
