package com.brighties.studentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Integer phoneNumber;
    private String goal;
    private String course;
    private Integer grade;

    private String schoolType;


}
