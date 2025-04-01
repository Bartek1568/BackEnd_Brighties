package com.brighties.teacherservice.mapper;

import com.brighties.teacherservice.dto.TeacherRequestDTO;
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

    public static Teacher toModel(TeacherRequestDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setSurname(teacherDTO.getSurname());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setAge(Integer.parseInt(teacherDTO.getAge()));
        teacher.setDescription(teacherDTO.getDescription());
        teacher.setPhoneNumber(Integer.parseInt(teacherDTO.getPhoneNumber()));
        return teacher;
    }

}
