package com.example.oopcrud.views;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserViews {

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/student")
    public String student(){
        return "student";
    }

    @GetMapping("/staff")
    public String staff(){
        return "staff";
    }


    @GetMapping("/profile")
    public String profile(){
        return "edit";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "create";
    }
}
