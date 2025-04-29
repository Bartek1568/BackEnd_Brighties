package com.brighties.userservice.controller;


import com.brighties.userservice.dto.request.TeacherRequestDTO;
import com.brighties.userservice.dto.response.TeacherResponseDTO;
import com.brighties.userservice.exception.TeacherNotFoundException;
import com.brighties.userservice.model.Role;
import com.brighties.userservice.service.impl.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @PostMapping("/register")
    public ResponseEntity<TeacherResponseDTO> create(@RequestBody @Valid TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO createdTeacher = teacherService.create(teacherRequestDTO);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getById(@PathVariable Long id) throws TeacherNotFoundException {
        TeacherResponseDTO teacher = teacherService.getById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAll() {
        List<TeacherResponseDTO> teachers = teacherService.getAll();
        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO updatedTeacher = teacherService.update(id, teacherRequestDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

