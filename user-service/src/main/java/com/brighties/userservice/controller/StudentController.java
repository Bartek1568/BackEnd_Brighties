package com.brighties.userservice.controller;

import com.brighties.userservice.dto.request.StudentRequestDTO;
import com.brighties.userservice.dto.response.StudentResponseDTO;
import com.brighties.userservice.exception.StudentNotFoundException;
import com.brighties.userservice.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    @PostMapping("/register")
    public ResponseEntity<StudentResponseDTO> create(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO createdStudent = studentService.create(studentRequestDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) throws StudentNotFoundException {
        StudentResponseDTO student = studentService.getById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        List<StudentResponseDTO> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id, @RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO updatedStudent = studentService.update(id, studentRequestDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
