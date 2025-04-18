package com.brighties.studentservice.controller;


import com.brighties.studentservice.dto.StudentRequestDTO;
import com.brighties.studentservice.dto.StudentResponseDTO;
import com.brighties.studentservice.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return  ResponseEntity.ok().body(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return  ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO studentRequest) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequest);

        return ResponseEntity.ok().body(studentResponseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequest) {

        StudentResponseDTO studentResponseDTO = studentService.updateStudent(id, studentRequest);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
