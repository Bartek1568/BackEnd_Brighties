package com.brighties.reservationservice.mapper;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationMapper {

    public static ReservationResponseDTO toDTO(Reservation reservation) {
        ReservationResponseDTO reservationDTO = new ReservationResponseDTO();
        reservationDTO.setId(reservation.getId().toString());
        reservationDTO.setTeacherId(reservation.getTeacherId().toString());
        reservationDTO.setStudentId(reservation.getStudentId().toString());
        reservationDTO.setDate(reservation.getDate().toString());
        reservationDTO.setStartTime(reservation.getStartTime().toString());
        reservationDTO.setEndTime(reservation.getEndTime().toString());
        reservationDTO.setStatus(reservation.getStatus().toString());
        reservationDTO.setNote(reservation.getNote());

        return reservationDTO;

    }

    public static Reservation toModel(ReservationRequestDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setTeacherId(Long.valueOf(reservationDTO.getTeacherId()));
        reservation.setStudentId(Long.valueOf(reservationDTO.getStudentId()));
        reservation.setDate(LocalDate.parse(reservationDTO.getDate()));
        reservation.setStartTime(LocalTime.parse(reservationDTO.getStartTime()));
        reservation.setEndTime(LocalTime.parse(reservationDTO.getEndTime()));
        reservation.setStatus(Reservation.Status.valueOf(reservationDTO.getStatus()));
        reservation.setNote(reservationDTO.getNote());

        return reservation;
    }
}
