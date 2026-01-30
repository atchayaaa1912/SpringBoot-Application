package org.example.demo.controllers;

import org.example.demo.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.demo.repositories.StudentRepository;
import java.util.List;

@RestController
public class Controller {
    private final StudentRepository studentRepository;
    public Controller(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/add_")
    public int add(
            @RequestParam int a,
            @RequestParam int b) {
        return a + b;
    }

    @GetMapping("/mul_")
    public int mul(
            @RequestParam int a,
            @RequestParam int b){
        return a * b ;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
