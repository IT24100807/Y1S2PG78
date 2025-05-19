package com.example.oopcrud.controllers;

import com.example.oopcrud.models.ExamLocationModel;
import com.example.oopcrud.services.ExamLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-locations")
public class ExamLocationController {

    @Autowired
    private ExamLocationService service;

    @GetMapping
    public List<ExamLocationModel> getAll() {
        return service.getAllExamLocations();
    }

    @PostMapping
    public String add(@RequestBody ExamLocationModel model) {
        service.addExamLocation(model);
        return "Exam location added.";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable int id, @RequestBody ExamLocationModel model) {
        service.updateExamLocation(id, model);
        return "Exam location updated.";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        service.deleteExamLocation(id);
        return "Exam location deleted.";
    }
    @GetMapping("/{id}")
    public ExamLocationModel getById(@PathVariable int id) {
        ExamLocationModel model = service.getExamLocationById(id);
        if (model == null) {
            throw new RuntimeException("Exam location not found with ID: " + id);
        }
        return model;
    }

}
