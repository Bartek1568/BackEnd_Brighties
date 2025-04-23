package com.brighties.studentservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    private Integer phoneNumber;

    @NotBlank(message = "Goal is required")
    private String goal;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Grade is required")
    private Integer grade;

    @NotBlank(message = "School type is required")
    private String schoolType;

}