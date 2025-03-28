package com.brighties.backend_brighties.repository;

import com.brighties.backend_brighties.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
