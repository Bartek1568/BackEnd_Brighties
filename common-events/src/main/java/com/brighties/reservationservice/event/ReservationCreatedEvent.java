package com.brighties.reservationservice.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationCreatedEvent {
    private Long reservationId;
    private Long teacherId;
    private Long studentId;

}
