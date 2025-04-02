package com.brighties.teacherservice.controller;

import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequest) {
        TeacherResponseDTO teacherResponseDTO = teacherService.createTeacher(teacherRequest);

        return ResponseEntity.ok().body(teacherResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long id,
                                                            @RequestBody TeacherRequestDTO teacherRequestDTO) {

        TeacherResponseDTO teacherResponseDTO = teacherService.updateTeacher(id,
                teacherRequestDTO);

        return ResponseEntity.ok().body(teacherResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
