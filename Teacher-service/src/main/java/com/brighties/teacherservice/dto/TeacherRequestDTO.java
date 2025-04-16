package com.brighties.teacherservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    @NotNull(message = "phone number is required")
    public  Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
