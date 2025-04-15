package com.brighties.reservationservice.mapper;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationMapper {

    public static ReservationResponseDTO toDTO(Reservation reservation) {
        ReservationResponseDTO reservationDTO = new ReservationResponseDTO();
        reservationDTO.setTeacherId(reservation.getTeacherId());
        reservationDTO.setStudentId(reservation.getStudentId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setNote(reservation.getNote());
        reservationDTO.setReservationId(reservation.getAvailabilitySlotId());

        return reservationDTO;

    }

    public static Reservation toModel(ReservationRequestDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getReservationId());
        reservation.setTeacherId(reservationDTO.getTeacherId());
        reservation.setStudentId(reservationDTO.getStudentId());
        reservation.setDate(reservationDTO.getDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setNote(reservationDTO.getNote());
        reservation.setAvailabilitySlotId(reservationDTO.getReservationId());

        return reservation;
    }
}
