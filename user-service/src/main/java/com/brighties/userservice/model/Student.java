package com.brighties.userservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends User {

    private Integer age;

    private String goal;

    private String course;

    private String schoolType;

    private Integer grade;
}
