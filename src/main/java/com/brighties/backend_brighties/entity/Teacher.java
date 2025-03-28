package com.brighties.backend_brighties.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "teachers")
public class Teacher {

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
    private String description;

    @OneToOne(mappedBy = "teacher")
    private Schedule schedule;

    @ElementCollection
    @CollectionTable(name = "teacher_courses", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "course")
    private List<Course> coursesToTeach;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Lesson> lessons;












}
