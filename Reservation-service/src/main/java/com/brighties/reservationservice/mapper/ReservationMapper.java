package com.brighties.reservationservice.mapper;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.model.Reservation;

public class ReservationMapper {

    public static ReservationResponseDTO toDTO(Reservation reservation) {
        ReservationResponseDTO reservationDTO = new ReservationResponseDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setTeacherId(reservation.getTeacherId());
        reservationDTO.setStudentId(reservation.getStudentId());
        reservationDTO.setAvailabilityId(reservation.getAvailabilitySlotId());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setNote(reservation.getNote());

        return reservationDTO;

    }

    public static Reservation toModel(ReservationRequestDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setTeacherId(reservationDTO.getTeacherId());
        reservation.setStudentId(reservationDTO.getStudentId());
        reservation.setAvailabilitySlotId(reservationDTO.getAvailabilityId());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setNote(reservationDTO.getNote());

        return reservation;
    }
}
