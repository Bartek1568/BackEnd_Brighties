package com.brighties.teacherservice.controller;

import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.model.Teacher;
import com.brighties.teacherservice.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;


    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getTeachers() {
        List<TeacherResponseDTO> teachers = teacherService.getTeachers();
        return ResponseEntity.ok().body(teachers);

    }
}
