package org.example.demo.controllers;

import org.example.demo.entities.Student;
import org.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.example.demo.repositories.StudentRepository;
import java.util.List;


@RequestMapping("/students")
@RestController
public class Controller {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final StudentRepository studentRepository;
    public Controller(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/createstudent")
    public Student createuser(@RequestBody Student user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return studentRepository.save(user);
    }

    @GetMapping("/{id}")
    public Student getUserById(@PathVariable Long id){
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Data Not Found at id:" + id));
    }

    @PutMapping("/{id}")
    public Student updateUser(@PathVariable Long id,@RequestBody Student user){
        Student studentdata = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Data Not Found at id:" + id));
        studentdata.setName(user.getName());
        studentdata.setEmail(user.getEmail());
        return studentRepository.save(studentdata);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteUser(@PathVariable Long id){
        Student studentdata=studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Data Not Found at id:" + id));
        studentRepository.delete(studentdata);
        return ResponseEntity.ok().build();
    }

}
