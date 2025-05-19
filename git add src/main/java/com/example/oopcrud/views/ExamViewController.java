package com.example.oopcrud.views;

import com.example.oopcrud.models.ExamLocationModel;
import com.example.oopcrud.services.ExamLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ExamViewController {
    @Autowired
    private ExamLocationService examLocationService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("locations",examLocationService.getAllExamLocations())
        ;        return  "index";
    }

    @GetMapping("/create")
    public String create(){
        return  "create";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable String id, Model model){
        int locationId = Integer.parseInt(id);
        ExamLocationModel locationModel= examLocationService.getExamLocationById(locationId);
        model.addAttribute("location",locationModel);
        return  "edit";
    }
}
