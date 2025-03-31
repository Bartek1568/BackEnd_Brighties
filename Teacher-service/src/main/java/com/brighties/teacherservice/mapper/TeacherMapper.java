package com.brighties.teacherservice.mapper;

import com.brighties.teacherservice.dto.TeacherResponseDTO;
import com.brighties.teacherservice.model.Teacher;

public class TeacherMapper {
    public static TeacherResponseDTO toDTO(Teacher teacher) {
        TeacherResponseDTO teacherDTO = new TeacherResponseDTO();
        teacherDTO.setId(teacher.getId().toString());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setSurname(teacher.getSurname());
        teacherDTO.setEmail(teacher.getEmail());
        teacherDTO.setAge(teacher.getAge().toString());
        teacherDTO.setDescription(teacher.getDescription());
        teacherDTO.setPhoneNumber(teacher.getPhoneNumber().toString());
        return teacherDTO;

    }
}
