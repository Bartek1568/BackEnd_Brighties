package com.brighties.reservationservice.repository;

import com.brighties.reservationservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStudentId(Long studentId);
    List<Reservation> findByTeacherId(Long teacherId);
    List<Reservation> findByStudentIdAndTeacherId(Long studentId, Long teacherId);
}
