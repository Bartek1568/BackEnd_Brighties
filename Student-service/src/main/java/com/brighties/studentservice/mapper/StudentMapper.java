package com.brighties.studentservice.mapper;

import com.brighties.studentservice.dto.StudentRequestDTO;
import com.brighties.studentservice.dto.StudentResponseDTO;
import com.brighties.studentservice.model.Student;

public class StudentMapper {

    public static StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO studentDTO = new StudentResponseDTO();
        studentDTO.setId(student.getId().toString());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setAge(student.getAge().toString());
        studentDTO.setCourse(student.getCourse());
        studentDTO.setGoal(student.getGoal().toString());
        studentDTO.setGrade(student.getGrade().toString());

        return studentDTO;
    }

    public static Student toModel(StudentRequestDTO studentDTO) {
        Student student = new Student();

        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setAge(Integer.valueOf(studentDTO.getAge()));
        student.setCourse(studentDTO.getCourse());
        student.setGoal(Student.Goal.valueOf(studentDTO.getGoal()));
        student.setGrade(Integer.valueOf(studentDTO.getGrade()));
        return student;
    }
}
