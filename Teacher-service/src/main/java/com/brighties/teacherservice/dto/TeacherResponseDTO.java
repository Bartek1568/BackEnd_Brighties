package com.brighties.teacherservice.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherResponseDTO {

    private String id;

    private String name;
    private String surname;
    private String email;
    private Integer age;
    private Integer phoneNumber;
    private String description;


}
