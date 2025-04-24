package com.brighties.reservationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class ReservationResponseDTO {

    private Long id;

    private Long teacherId;

    private Long studentId;

    private Long availabilityId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    private String note;


}
