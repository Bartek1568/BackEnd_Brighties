package com.brighties.reservationservice;

import com.brighties.reservationservice.model.Reservation;
import com.brighties.reservationservice.repository.ReservationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation createSampleReservation() {
        return Reservation.builder()
                .teacherId(1L)
                .studentId(2L)
                .availabilitySlotId(3L)
                .status("PENDING")
                .note("Test note")
                .build();
    }

    @Test
    @Order(1)
    public void saveReservation_shouldPersistReservation() {
        // given
        Reservation reservation = createSampleReservation();

        // when
        Reservation saved = reservationRepository.save(reservation);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void getReservationById_shouldReturnReservation() {
        // given
        Reservation reservation = reservationRepository.save(createSampleReservation());

        // when
        Optional<Reservation> found = reservationRepository.findById(reservation.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getStatus()).isEqualTo("PENDING");
    }

    @Test
    @Order(3)
    public void getAllReservations_shouldReturnNonEmptyList() {
        // given
        reservationRepository.save(createSampleReservation());
        reservationRepository.save(createSampleReservation());

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).isNotEmpty();
        assertThat(reservations.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @Order(4)
    public void updateReservation_shouldPersistChanges() {
        // given
        Reservation reservation = reservationRepository.save(createSampleReservation());

        // when
        reservation.setStatus("CONFIRMED");
        Reservation updated = reservationRepository.save(reservation);

        // then
        assertThat(updated.getStatus()).isEqualTo("CONFIRMED");
    }

    @Test
    @Order(5)
    public void deleteReservation_shouldRemoveReservation() {
        // given
        Reservation reservation = reservationRepository.save(createSampleReservation());
        Long id = reservation.getId();

        // when
        reservationRepository.deleteById(id);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(id);
        assertThat(deleted).isEmpty();
    }

    @Test
    @Order(6)
    public void findByStudentId_shouldReturnCorrectReservations() {
        // given
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);

        // when
        List<Reservation> results = reservationRepository.findByStudentId(2L);

        // then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getStudentId()).isEqualTo(2L);
    }

    @Test
    @Order(7)
    public void findByTeacherId_shouldReturnCorrectReservations() {
        // given
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);

        // when
        List<Reservation> results = reservationRepository.findByTeacherId(1L);

        // then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getTeacherId()).isEqualTo(1L);
    }

    @Test
    @Order(8)
    public void findByStudentIdAndTeacherId_shouldReturnCorrectReservations() {
        // given
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);

        // when
        List<Reservation> results = reservationRepository.findByStudentIdAndTeacherId(2L, 1L);

        // then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getStudentId()).isEqualTo(2L);
        assertThat(results.get(0).getTeacherId()).isEqualTo(1L);
    }
}
