package com.brighties.userservice.service.impl;

import com.brighties.userservice.dto.request.StudentRequestDTO;
import com.brighties.userservice.dto.response.StudentResponseDTO;
import com.brighties.userservice.exception.EmailAlreadyExistsException;
import com.brighties.userservice.exception.PhoneNumberExistsException;
import com.brighties.userservice.exception.StudentNotFoundException;
import com.brighties.userservice.mapper.StudentMapper;
import com.brighties.userservice.model.Role;
import com.brighties.userservice.model.Student;
import com.brighties.userservice.repository.StudentRepository;
import com.brighties.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements UserService<StudentResponseDTO, StudentRequestDTO> {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponseDTO> getAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO getById(Long id) throws StudentNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return StudentMapper.toDTO(student);
    }

    @Override
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else if (studentRepository.existsByPhoneNumber(studentRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        studentRequestDTO.setRole(Role.STUDENT);
        Student newStudent = studentRepository.save(StudentMapper.toModel(studentRequestDTO));
        return StudentMapper.toDTO(newStudent);
    }

    @Override
    public StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("student not found with id: " + id));

        student.setName(studentRequestDTO.getName());
        student.setSurname(studentRequestDTO.getSurname());
        student.setAge(studentRequestDTO.getAge());
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        student.setEmail(studentRequestDTO.getEmail());
        if (studentRepository.existsByPhoneNumber(studentRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        student.setPhoneNumber(studentRequestDTO.getPhoneNumber());
        student.setGoal(studentRequestDTO.getGoal());
        student.setCourse(studentRequestDTO.getCourse());
        student.setGrade(studentRequestDTO.getGrade());
        student.setSchoolType(studentRequestDTO.getSchoolType());

        Student updatedStudent = studentRepository.save(student);
        return StudentMapper.toDTO(updatedStudent);
    }

    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }


}
