package com.brighties.reservationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class ReservationRequestDTO {

    private Long teacherId;

    private Long studentId;

    private Long availabilityId;

    private LocalDate date;



    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    private String note;

}
