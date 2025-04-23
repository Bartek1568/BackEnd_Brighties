package com.brighties.teacherservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherRequestDTO {



    @NotBlank(message = "Name is required")
    private String name;


    @NotBlank (message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "surname is required")
    private String surname;

    @NotNull(message = "age is required")
    private Integer age;

    @NotNull(message = "phone number is required")
    private Integer phoneNumber;

    @NotBlank(message = "description is required")
    private String description;


}
