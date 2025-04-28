package com.brighties.userservice.service.impl;

import com.brighties.userservice.dto.request.StudentRequestDTO;
import com.brighties.userservice.dto.response.StudentResponseDTO;
import com.brighties.userservice.exception.EmailAlreadyExistsException;
import com.brighties.userservice.exception.PhoneNumberExistsException;
import com.brighties.userservice.exception.StudentNotFoundException;
import com.brighties.userservice.mapper.StudentMapper;
import com.brighties.userservice.model.Role;
import com.brighties.userservice.model.StudentProfile;
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
        List<StudentProfile> studentProfiles = studentRepository.findAll();
        return studentProfiles.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO getById(Long id) throws StudentNotFoundException {
        StudentProfile studentProfile = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return StudentMapper.toDTO(studentProfile);
    }

    @Override
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else if (studentRepository.existsByPhoneNumber(studentRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        studentRequestDTO.setRole(Role.STUDENT);
        StudentProfile newStudentProfile = studentRepository.save(StudentMapper.toModel(studentRequestDTO));
        return StudentMapper.toDTO(newStudentProfile);
    }

    @Override
    public StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO) {
        StudentProfile studentProfile = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("student not found with id: " + id));

        studentProfile.setName(studentRequestDTO.getName());
        studentProfile.setSurname(studentRequestDTO.getSurname());
        studentProfile.setAge(studentRequestDTO.getAge());
        if (!studentProfile.getEmail().equals(studentRequestDTO.getEmail()) && studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        studentProfile.setEmail(studentRequestDTO.getEmail());
        if (!studentProfile.getPhoneNumber().equals(studentRequestDTO.getPhoneNumber()) && studentRepository.existsByPhoneNumber(studentRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        studentProfile.setPhoneNumber(studentRequestDTO.getPhoneNumber());
        studentProfile.setGoal(studentRequestDTO.getGoal());
        studentProfile.setCourse(studentRequestDTO.getCourse());
        studentProfile.setGrade(studentRequestDTO.getGrade());
        studentProfile.setSchoolType(studentRequestDTO.getSchoolType());

        StudentProfile updatedStudentProfile = studentRepository.save(studentProfile);
        return StudentMapper.toDTO(updatedStudentProfile);
    }

    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }


}
