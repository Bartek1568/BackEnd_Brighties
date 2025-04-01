package com.brighties.teacherservice.service;

import com.brighties.teacherservice.dto.TeacherRequestDTO;
import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.mapper.TeacherMapper;
import com.brighties.teacherservice.model.Teacher;
import com.brighties.teacherservice.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDTO> getTeachers(){
         List<Teacher> teachers = teacherRepository.findAll();

         return teachers.stream().
                 map(teacher -> TeacherMapper.toDTO(teacher)).collect(Collectors.toList());
    }

    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO){
        Teacher newTeacher = teacherRepository.save(
                TeacherMapper.toModel(teacherRequestDTO));

        return TeacherMapper.toDTO(newTeacher);
    }
}
