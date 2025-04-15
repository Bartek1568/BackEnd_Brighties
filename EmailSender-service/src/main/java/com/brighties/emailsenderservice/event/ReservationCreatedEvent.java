package com.brighties.emailsenderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreatedEvent {
    private Long reservationId;
    private Long teacherId;
    private Long studentId;
    private String date;
    private String startTime;
    private String endTime;
}
