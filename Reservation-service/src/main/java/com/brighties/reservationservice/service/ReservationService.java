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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO){
        checkIfStudentAndTeacherExists(reservationRequestDTO);

        Reservation newReservation = reservationRepository.save(ReservationMapper.toModel(reservationRequestDTO));

        publishReservationEvents(newReservation);

        return ReservationMapper.toDTO(newReservation);
    }

    private void publishReservationEvents(Reservation reservation) {
        SlotReservedEvent slotEvent = new SlotReservedEvent(
                reservation.getTeacherId(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );
        eventPublisher.sendSlotReservedEvent(slotEvent);

        ReservationCreatedEvent createdEvent = new ReservationCreatedEvent(
                reservation.getId(),
                reservation.getTeacherId(),
                reservation.getStudentId(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );
        eventPublisher.sendReservationCreatedEvent(createdEvent);
    }


    private void checkIfStudentAndTeacherExists(ReservationRequestDTO reservationRequestDTO){
        Long studentId = Long.valueOf(reservationRequestDTO.getStudentId());

        if (!studentGrpcClient.checkStudentExists(studentId)) {
            throw new IllegalArgumentException("Student with ID " + studentId + " does not exist");
        }

        boolean isAvailable = availabilityGrpcClient.checkSlotAvailable(
                reservationRequestDTO.getTeacherId(),
                reservationRequestDTO.getDate(),
                reservationRequestDTO.getDayOfWeek(),
                reservationRequestDTO.getStartTime(),
                reservationRequestDTO.getEndTime()
        );

        if (!isAvailable) {
            System.out.println("Received checkSlotAvailability request:");
            System.out.println("Teacher ID: " + reservationRequestDTO.getTeacherId());
            System.out.println("Date: " + reservationRequestDTO.getDate());
            System.out.println("Day of Week: " + reservationRequestDTO.getDayOfWeek());
            System.out.println("Start Time: " + reservationRequestDTO.getStartTime());
            System.out.println("End Time: " + reservationRequestDTO.getEndTime());

            throw new IllegalArgumentException("Selected slot is not available");

        }

    }

    public void deleteReservation(Long reservationId){
        reservationRepository.deleteById(reservationId);
    }

    public ReservationResponseDTO updateReservation(Long reservationId, ReservationRequestDTO reservationRequestDTO){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        reservation.get().setTeacherId(reservationRequestDTO.getTeacherId());
        reservation.get().setStudentId(reservationRequestDTO.getStudentId());
        reservation.get().setDate(reservationRequestDTO.getDate());
        reservation.get().setStartTime(reservationRequestDTO.getStartTime());
        reservation.get().setEndTime(reservationRequestDTO.getEndTime());
        reservation.get().setStatus(reservationRequestDTO.getStatus());
        reservation.get().setNote(reservationRequestDTO.getNote());

        return ReservationMapper.toDTO(reservationRepository.save(reservation.get()));
    }


}
