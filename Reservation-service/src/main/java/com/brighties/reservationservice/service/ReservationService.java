package com.brighties.reservationservice.service;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.mapper.ReservationMapper;
import com.brighties.reservationservice.model.Reservation;
import com.brighties.reservationservice.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDTO> getReservationsByTeacherId(Long teacherId){
        List<Reservation> reservations = reservationRepository.findByTeacherId(teacherId);

        return  reservations.stream().map(reservation -> ReservationMapper.toDTO(reservation)).
                collect(Collectors.toList());
    }

    public List<ReservationResponseDTO> getReservationsByStudentId(Long studentId){
        List<Reservation> reservations = reservationRepository.findByStudentId(studentId);

        return  reservations.stream().map(reservation -> ReservationMapper.toDTO(reservation)).
                collect(Collectors.toList());
    }

    public List<ReservationResponseDTO> getReservationsByStudentIdAndTeacherId(Long studentId, Long teacherId){
        List<Reservation> reservations = reservationRepository.findByStudentIdAndTeacherId(studentId, teacherId);

        return  reservations.stream().map(reservation -> ReservationMapper.toDTO(reservation)).
                collect(Collectors.toList());
    }

    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO){
        Reservation newReservation = reservationRepository.save(ReservationMapper.toModel(reservationRequestDTO));

        return ReservationMapper.toDTO(newReservation);

    }

    public void deleteReservation(Long reservationId){
        reservationRepository.deleteById(reservationId);
    }

    public ReservationResponseDTO updateReservation(Long reservationId, ReservationRequestDTO reservationRequestDTO){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        reservation.get().setTeacherId(Long.valueOf(reservationRequestDTO.getTeacherId()));
        reservation.get().setStudentId(Long.valueOf(reservationRequestDTO.getStudentId()));
        reservation.get().setDate(LocalDate.parse(reservationRequestDTO.getDate()));
        reservation.get().setStartTime(LocalTime.parse(reservationRequestDTO.getStartTime()));
        reservation.get().setEndTime(LocalTime.parse(reservationRequestDTO.getEndTime()));
        reservation.get().setStatus(Reservation.Status.valueOf(reservationRequestDTO.getStatus()));
        reservation.get().setNote(reservationRequestDTO.getNote());
    }


}
