package com.brighties.userservice.mapper;

import com.brighties.userservice.dto.request.StudentRequestDTO;
import com.brighties.userservice.dto.response.StudentResponseDTO;
import com.brighties.userservice.model.Student;

public class StudentMapper {

    public static StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO studentDTO = new StudentResponseDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setAge(student.getAge());
        studentDTO.setCourse(student.getCourse());
        studentDTO.setGoal(student.getGoal());
        studentDTO.setGrade(student.getGrade());
        studentDTO.setSchoolType(student.getSchoolType());

        return studentDTO;
    }

    public static Student toModel(StudentRequestDTO studentDTO) {
        Student student = new Student();

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setAge(studentDTO.getAge());
        student.setCourse(studentDTO.getCourse());
        student.setGoal(studentDTO.getGoal());
        student.setGrade(studentDTO.getGrade());
        student.setSchoolType(studentDTO.getSchoolType());
        return student;
    }
}
