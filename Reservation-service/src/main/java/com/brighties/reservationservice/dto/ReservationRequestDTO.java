package com.brighties.reservationservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationRequestDTO {

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Availability ID is required")
    private Long availabilityId;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Note is required")
    @Size(max = 500, message = "Note cannot exceed 500 characters")
    private String note;

}
