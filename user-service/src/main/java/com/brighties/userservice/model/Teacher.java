package com.brighties.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    private String description;

    private List<String> specializations;

}
