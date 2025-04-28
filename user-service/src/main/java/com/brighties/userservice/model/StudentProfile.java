package com.brighties.userservice.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("STUDENT")
public class StudentProfile extends User {

    private Integer age;

    private String goal;

    private String course;

    private String schoolType;

    private Integer grade;
}
