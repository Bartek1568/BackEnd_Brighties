package com.brighties.userservice.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponseDTO extends UserResponseDTO {


    private Integer age;
    private String goal;
    private String course;
    private String schoolType;
    private Integer grade;
}
