package com.brighties.availabilityservice.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class AvailabilitySlotResponseDTO {

    private Long id;

    private Long teacherId;

    private LocalDate date;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean isAvailable;

    private boolean isReoccurringWeekly;

}

