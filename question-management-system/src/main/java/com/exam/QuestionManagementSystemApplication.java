package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.exam", "com.exam.controller", "com.exam.service", "com.exam.repository"})
public class QuestionManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionManagementSystemApplication.class, args);
    }
}