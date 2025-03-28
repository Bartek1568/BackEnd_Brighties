package com.brighties.backend_brighties.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "available_term_id", nullable = false)
    private List<AvailableTerm> availableTerms;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
}
