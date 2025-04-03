package com.brighties.studentservice.service;

import com.brighties.studentservice.dto.StudentResponseDTO;
import com.brighties.studentservice.mapper.StudentMapper;
import com.brighties.studentservice.model.Student;
import com.brighties.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students =  studentRepository.findAll();

        return students.stream().
                map(student ->StudentMapper.toDTO(student))
                .collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return StudentMapper.toDTO(student.get());
    }
}
