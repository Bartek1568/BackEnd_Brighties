package com.brighties.studentservice.service;

import com.brighties.studentservice.dto.StudentRequestDTO;
import com.brighties.studentservice.dto.StudentResponseDTO;
import com.brighties.studentservice.exception.EmailAlreadyExistsException;
import com.brighties.studentservice.exception.PhoneNumberExistsException;
import com.brighties.studentservice.exception.StudentNotFoundException;
import com.brighties.studentservice.mapper.StudentMapper;
import com.brighties.studentservice.model.Student;
import com.brighties.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
                map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(Long id) throws StudentNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return StudentMapper.toDTO(student);
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {

        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else if(studentRepository.existsByPhoneNumber(studentRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberExistsException("Phone number already exists");
        }
        Student newStudent = studentRepository.
                save(StudentMapper.toModel(studentRequestDTO));

        return StudentMapper.toDTO(newStudent);
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("student not found with id: " + id));

        student.setName(studentRequestDTO.getName());
        student.setSurname(studentRequestDTO.getSurname());
        student.setAge(studentRequestDTO.getAge());
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        student.setEmail(studentRequestDTO.getEmail());
        if (studentRepository.existsByPhoneNumber(studentRequestDTO.getPhoneNumber())){
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


    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
