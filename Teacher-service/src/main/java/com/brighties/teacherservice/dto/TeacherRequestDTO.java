package com.brighties.teacherservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRequestDTO {



    @NotBlank(message = "Name is required")
    private String name;


    @NotBlank (message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "surname is required")
    private String surname;

    @NotBlank(message = "age is required")
    private String age;

    @NotBlank(message = "phone number is required")
    private String phoneNumber;

    @NotBlank(message = "description is required")
    private String description;
}
