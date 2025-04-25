package com.brighties.availabilityservice.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AvailabilitySlotRequestDTO {

    @NotNull(message = "Teacher id is required")
    private Long teacherId;

    @NotNull(message = "Date is  required")
    @FutureOrPresent
    private LocalDate date;

    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "is Available is required")
    private boolean isAvailable;

    @NotNull(message = "Is reoccurring weekly is required")
    private boolean isReoccurringWeekly;
}
