package com.brighties.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("TEACHER")
public class TeacherProfile extends User {

    private String description;

    @ElementCollection
    @CollectionTable(name = "teacher_specializations", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "specialization")
    private List<String> specializations;

}
