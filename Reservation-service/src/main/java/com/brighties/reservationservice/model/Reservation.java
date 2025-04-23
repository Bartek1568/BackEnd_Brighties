package com.brighties.reservationservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teacherId;

    private Long studentId;

    private Long availabilitySlotId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    private String note;
}
