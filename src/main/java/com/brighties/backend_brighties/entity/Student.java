package com.brighties.backend_brighties.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int phoneNumber;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private SchoolType schoolType;

    @Column(nullable = false)
    private String goal;

    @ElementCollection
    @Column(name = "course")
    private List<Course> enrolledCourses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Reservation> reservations;





}
