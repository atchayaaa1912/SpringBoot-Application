package org.example.demo.services;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.entities.Student;
import org.example.demo.exceptions.UnauthorizedActionException;
import org.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // CREATE
    public Student createStudent(Student student){

        log.info("Creating new student");

        Student savedStudent = studentRepository.save(student);

        savedStudent.setCreatedBy(savedStudent.getId());
        savedStudent.setModifiedBy(savedStudent.getId());

        log.debug("Student created with ID: {}", savedStudent.getId());

        return studentRepository.save(savedStudent);
    }

    public Student updateStudent(Long id, Student student) throws UnauthorizedActionException {

        log.info("Updating student with ID: {}", id);

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new RuntimeException("Student not found");
                });

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Unauthenticated update attempt");
            throw new UnauthorizedActionException("User is not authenticated");
        }

        String username = authentication.getName();
        log.debug("Logged in username: {}", username);

        Long loggedInUserId = studentRepository.findIdByUsername(username)
                .orElseThrow(() -> new UnauthorizedActionException("User not found in system"));

        if (!loggedInUserId.equals(existing.getId())) {
            log.warn("Unauthorized update attempt by user ID: {}", loggedInUserId);
            throw new UnauthorizedActionException(
                    "Only logged-in user can update their own profile"
            );

        }

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setModifiedBy(loggedInUserId);

        log.info("Student updated successfully with ID: {}", id);

        return studentRepository.save(existing);
    }

}