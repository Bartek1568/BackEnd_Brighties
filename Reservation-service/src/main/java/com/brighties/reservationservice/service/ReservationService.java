package com.brighties.reservationservice.service;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.event.ReservationCreatedEvent;
import com.brighties.reservationservice.event.SlotReservedEvent;
import com.brighties.reservationservice.grpc.AvailabilityGrpcClient;
import com.brighties.reservationservice.grpc.StudentGrpcClient;
import com.brighties.reservationservice.mapper.ReservationMapper;
import com.brighties.reservationservice.model.Reservation;
import com.brighties.reservationservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private final StudentGrpcClient studentGrpcClient;
    private final AvailabilityGrpcClient availabilityGrpcClient;
    private final ReservationEventPublisher eventPublisher;

    public ReservationService(ReservationRepository reservationRepository, StudentGrpcClient studentGrpcClient,
                              AvailabilityGrpcClient availabilityGrpcClient, ReservationEventPublisher eventPublisher) {
        this.reservationRepository = reservationRepository;
        this.studentGrpcClient = studentGrpcClient;
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.eventPublisher = eventPublisher;
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
        checkIfStudentAndTeacherExists(reservationRequestDTO);

        Reservation newReservation = reservationRepository.save(ReservationMapper.toModel(reservationRequestDTO));

        publishReservationEvents(newReservation);

        return ReservationMapper.toDTO(newReservation);
    }

    private void publishReservationEvents(Reservation reservation) {
        SlotReservedEvent slotEvent = new SlotReservedEvent(
                reservation.getTeacherId(),
                reservation.getDate().toString(),
                reservation.getStartTime().toString(),
                reservation.getEndTime().toString()
        );
        eventPublisher.sendSlotReservedEvent(slotEvent);

        ReservationCreatedEvent createdEvent = new ReservationCreatedEvent(
                reservation.getId(),
                reservation.getTeacherId(),
                reservation.getStudentId(),
                reservation.getDate().toString(),
                reservation.getStartTime().toString(),
                reservation.getEndTime().toString()
        );
        eventPublisher.sendReservationCreatedEvent(createdEvent);
    }


    private void checkIfStudentAndTeacherExists(ReservationRequestDTO reservationRequestDTO){
        Long studentId = Long.valueOf(reservationRequestDTO.getStudentId());

        if (!studentGrpcClient.checkStudentExists(studentId)) {
            throw new IllegalArgumentException("Student with ID " + studentId + " does not exist");
        }

        boolean isAvailable = availabilityGrpcClient.checkSlotAvailable(
                Long.valueOf(reservationRequestDTO.getReservationId()),
                reservationRequestDTO.getDate(),
                reservationRequestDTO.getStartTime(),
                reservationRequestDTO.getEndTime()
        );

        if (!isAvailable) {
            throw new IllegalArgumentException("Selected slot is not available");
        }
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

        return ReservationMapper.toDTO(reservationRepository.save(reservation.get()));
    }


}
