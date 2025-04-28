package com.brighties.userservice.mapper;


import com.brighties.userservice.dto.request.TeacherRequestDTO;
import com.brighties.userservice.dto.response.TeacherResponseDTO;
import com.brighties.userservice.model.Teacher;

public class TeacherMapper {

    public static TeacherResponseDTO toDTO(Teacher teacher) {
        TeacherResponseDTO teacherDTO = new TeacherResponseDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setSurname(teacher.getSurname());
        teacherDTO.setEmail(teacher.getEmail());
        teacherDTO.setDescription(teacher.getDescription());
        teacherDTO.setPhoneNumber(teacher.getPhoneNumber());
        return teacherDTO;

    }

    public static Teacher toModel(TeacherRequestDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setSurname(teacherDTO.getSurname());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setDescription(teacherDTO.getDescription());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        return teacher;
    }


}
