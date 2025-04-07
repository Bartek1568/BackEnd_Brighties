package com.brighties.availabilityservice.dto;

import jakarta.validation.constraints.NotBlank;

public class AvailabilitySlotRequestDTO {

    @NotBlank
    private String teacherId;

    @NotBlank
    private String date;

    @NotBlank
    private String dayOfWeek;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;
}
