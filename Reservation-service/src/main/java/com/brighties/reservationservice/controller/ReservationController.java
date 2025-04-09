package com.brighties.reservationservice.controller;


import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByTeacherId(@RequestParam Long teacherId) {

        List<ReservationResponseDTO> reservations = reservationService.getReservationsByTeacherId(teacherId);

        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByStudentId(@RequestParam Long studentId) {

        List<ReservationResponseDTO> reservations = reservationService.getReservationsByStudentId(studentId);

        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getReservationByStudentIdAndTeacherId(@RequestParam Long studentId, @RequestParam Long teacherId) {

        List<ReservationResponseDTO> reservations = reservationService.getReservationsByStudentIdAndTeacherId(studentId, teacherId);

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {

        ReservationResponseDTO reservationResponseDTO = reservationService.createReservation(reservationRequestDTO);
        return ResponseEntity.ok().body(reservationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> deleteReservation(@PathVariable Long id) {

        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDTO reservationRequestDTO) {

        ReservationResponseDTO reservationResponseDTO = reservationService.updateReservation(id, reservationRequestDTO);
        return ResponseEntity.ok().body(reservationResponseDTO);
    }
}

