package com.brighties.reservationservice.service;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.event.ReservationCreatedEvent;
import com.brighties.reservationservice.event.SlotReservedEvent;
import com.brighties.reservationservice.exception.AvailabilitySlotIsAlreadyReservedException;
import com.brighties.reservationservice.exception.StudentNotFoundException;
import com.brighties.reservationservice.exception.TeacherNotFoundException;
import com.brighties.reservationservice.grpc.AvailabilityGrpcClient;
import com.brighties.reservationservice.grpc.UserGrpcClient;
import com.brighties.reservationservice.mapper.ReservationMapper;
import com.brighties.reservationservice.model.Reservation;
import com.brighties.reservationservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private final AvailabilityGrpcClient availabilityGrpcClient;
    private final UserGrpcClient userGrpcClient;
    private final ReservationEventPublisher eventPublisher;

    public ReservationService(ReservationRepository reservationRepository,
                              AvailabilityGrpcClient availabilityGrpcClient, ReservationEventPublisher eventPublisher, UserGrpcClient userGrpcClient) {
        this.reservationRepository = reservationRepository;
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.userGrpcClient = userGrpcClient;
        this.eventPublisher = eventPublisher;
    }

    public List<ReservationResponseDTO> getReservationsByTeacherId(Long teacherId){
        List<Reservation> reservations = reservationRepository.findByTeacherId(teacherId);

        return  reservations.stream().map(ReservationMapper::toDTO).
                collect(Collectors.toList());
    }

    public List<ReservationResponseDTO> getReservationsByStudentId(Long studentId){
        List<Reservation> reservations = reservationRepository.findByStudentId(studentId);

        return  reservations.stream().map(ReservationMapper::toDTO).
                collect(Collectors.toList());
    }

    public List<ReservationResponseDTO> getReservationsByStudentIdAndTeacherId(Long studentId, Long teacherId){
        List<Reservation> reservations = reservationRepository.findByStudentIdAndTeacherId(studentId, teacherId);

        return  reservations.stream().map(ReservationMapper::toDTO).
                collect(Collectors.toList());
    }


    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO){

        checkIfStudentAndTeacherExists(reservationRequestDTO);
        checkIfAvailabilitySlotIsAlreadyReserved(reservationRequestDTO);

        Reservation newReservation = reservationRepository.save(ReservationMapper.toModel(reservationRequestDTO));

        publishReservationEvents(newReservation);

        return ReservationMapper.toDTO(newReservation);
    }

    private void publishReservationEvents(Reservation reservation) {
        SlotReservedEvent slotEvent = new SlotReservedEvent(
                reservation.getAvailabilitySlotId()
        );
        eventPublisher.sendSlotReservedEvent(slotEvent);

        ReservationCreatedEvent createdEvent = new ReservationCreatedEvent(
                reservation.getId(),
                reservation.getTeacherId(),
                reservation.getStudentId()

        );
        eventPublisher.sendReservationCreatedEvent(createdEvent);
    }

    private void checkIfStudentAndTeacherExists(ReservationRequestDTO reservationRequestDTO){
        Long studentId = reservationRequestDTO.getStudentId();
        Long teacherId = reservationRequestDTO.getTeacherId();

        if(!userGrpcClient.checkUserExistenceByRole(studentId,"STUDENT")){
            throw new StudentNotFoundException("Student with ID " + studentId + " does not exist");
        }
        if(!userGrpcClient.checkUserExistenceByRole(teacherId,"TEACHER")){
            throw new TeacherNotFoundException("Teacher with ID " + reservationRequestDTO.getTeacherId() + " does not exist");
        }


    }
    private void checkIfAvailabilitySlotIsAlreadyReserved(ReservationRequestDTO reservationRequestDTO){
        boolean isAvailable = availabilityGrpcClient.checkSlotAvailable(
                reservationRequestDTO.getAvailabilityId()
        );

        if (!isAvailable) {
            throw new AvailabilitySlotIsAlreadyReservedException("Selected slot is not available");

        }
    }

    public void deleteReservation(Long reservationId){
        reservationRepository.deleteById(reservationId);
    }

    public ReservationResponseDTO updateReservation(Long reservationId, ReservationRequestDTO reservationRequestDTO){
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        checkIfAvailabilitySlotIsAlreadyReserved(reservationRequestDTO);
        checkIfStudentAndTeacherExists(reservationRequestDTO);

        reservation.get().setTeacherId(reservationRequestDTO.getTeacherId());
        reservation.get().setStudentId(reservationRequestDTO.getStudentId());
        reservation.get().setAvailabilitySlotId(reservationRequestDTO.getAvailabilityId());
        reservation.get().setStatus(reservationRequestDTO.getStatus());
        reservation.get().setNote(reservationRequestDTO.getNote());

        return ReservationMapper.toDTO(reservationRepository.save(reservation.get()));
    }


}
