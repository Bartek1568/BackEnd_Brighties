package com.brighties.studentservice.service;

import com.brighties.studentservice.dto.StudentRequestDTO;
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

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        Student newStudent = studentRepository.
                save(StudentMapper.toModel(studentRequestDTO));

        return StudentMapper.toDTO(newStudent);
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        Optional<Student> student = studentRepository.findById(id);

        student.get().setName(studentRequestDTO.getName());
        student.get().setSurname(studentRequestDTO.getSurname());
        student.get().setAge(Integer.valueOf(studentRequestDTO.getAge()));
        student.get().setEmail(studentRequestDTO.getEmail());
        student.get().setPhoneNumber(studentRequestDTO.getPhoneNumber());
        student.get().setGoal(Student.Goal.valueOf(studentRequestDTO.getGoal()));
        student.get().setCourse(studentRequestDTO.getCourse());
        student.get().setGrade(Integer.valueOf(studentRequestDTO.getGrade()));

        Student updatedStudent = studentRepository.save(student.get());
        return StudentMapper.toDTO(updatedStudent);


    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
