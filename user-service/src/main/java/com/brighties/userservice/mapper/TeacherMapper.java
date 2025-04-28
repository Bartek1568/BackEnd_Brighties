package com.brighties.userservice.mapper;


import com.brighties.userservice.dto.request.TeacherRequestDTO;
import com.brighties.userservice.dto.response.TeacherResponseDTO;
import com.brighties.userservice.model.TeacherProfile;

public class TeacherMapper {

    public static TeacherResponseDTO toDTO(TeacherProfile teacherProfile) {
        TeacherResponseDTO teacherDTO = new TeacherResponseDTO();
        teacherDTO.setId(teacherProfile.getId());
        teacherDTO.setName(teacherProfile.getName());
        teacherDTO.setSurname(teacherProfile.getSurname());
        teacherDTO.setEmail(teacherProfile.getEmail());
        teacherDTO.setDescription(teacherProfile.getDescription());
        teacherDTO.setPhoneNumber(teacherProfile.getPhoneNumber());
        teacherDTO.setSpecializations(teacherProfile.getSpecializations());
        return teacherDTO;

    }

    public static TeacherProfile toModel(TeacherRequestDTO teacherDTO) {
        TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setName(teacherDTO.getName());
        teacherProfile.setSurname(teacherDTO.getSurname());
        teacherProfile.setEmail(teacherDTO.getEmail());
        teacherProfile.setPassword(teacherDTO.getPassword());
        teacherProfile.setDescription(teacherDTO.getDescription());
        teacherProfile.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacherProfile.setRole(teacherDTO.getRole());
        teacherProfile.setSpecializations(teacherDTO.getSpecializations());
        return teacherProfile;
    }


}
