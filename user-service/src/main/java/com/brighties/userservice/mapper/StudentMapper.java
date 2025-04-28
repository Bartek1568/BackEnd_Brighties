package com.brighties.userservice.mapper;

import com.brighties.userservice.dto.request.StudentRequestDTO;
import com.brighties.userservice.dto.response.StudentResponseDTO;
import com.brighties.userservice.model.StudentProfile;

public class StudentMapper {

    public static StudentResponseDTO toDTO(StudentProfile studentProfile) {
        StudentResponseDTO studentDTO = new StudentResponseDTO();
        studentDTO.setId(studentProfile.getId());
        studentDTO.setName(studentProfile.getName());
        studentDTO.setSurname(studentProfile.getSurname());
        studentDTO.setEmail(studentProfile.getEmail());
        studentDTO.setPhoneNumber(studentProfile.getPhoneNumber());
        studentDTO.setAge(studentProfile.getAge());
        studentDTO.setCourse(studentProfile.getCourse());
        studentDTO.setGoal(studentProfile.getGoal());
        studentDTO.setGrade(studentProfile.getGrade());
        studentDTO.setSchoolType(studentProfile.getSchoolType());

        return studentDTO;
    }

    public static StudentProfile toModel(StudentRequestDTO studentDTO) {
        StudentProfile studentProfile = new StudentProfile();

        studentProfile.setName(studentDTO.getName());
        studentProfile.setSurname(studentDTO.getSurname());
        studentProfile.setEmail(studentDTO.getEmail());
        studentProfile.setPassword(studentDTO.getPassword());
        studentProfile.setPhoneNumber(studentDTO.getPhoneNumber());
        studentProfile.setAge(studentDTO.getAge());
        studentProfile.setCourse(studentDTO.getCourse());
        studentProfile.setGoal(studentDTO.getGoal());
        studentProfile.setGrade(studentDTO.getGrade());
        studentProfile.setSchoolType(studentDTO.getSchoolType());
        studentProfile.setRole(studentDTO.getRole());
        return studentProfile;
    }
}
