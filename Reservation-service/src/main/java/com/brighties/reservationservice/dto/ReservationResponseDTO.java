package com.brighties.reservationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationResponseDTO {

    private Long id;

    private Long teacherId;

    private Long studentId;

    private Long availabilityId;

    private String status;

    private String note;


}
