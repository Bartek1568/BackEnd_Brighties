package com.brighties.availabilityservice.controller;


import com.brighties.availabilityservice.dto.AvailabilitySlotRequestDTO;
import com.brighties.availabilityservice.dto.AvailabilitySlotResponseDTO;
import com.brighties.availabilityservice.service.AvailabilitySlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availabilities")
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    public AvailabilitySlotController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    @GetMapping
    public ResponseEntity<List<AvailabilitySlotResponseDTO>> getAvailabilitySlotsByTeacherAndDate(@RequestParam Long teacherId, @RequestParam LocalDate date) {

        List<AvailabilitySlotResponseDTO> availabilitySlots = availabilitySlotService.getAvailabilityByTeacherAndDate(teacherId, date);

        return ResponseEntity.ok().body(availabilitySlots);
    }

    @PostMapping
    public ResponseEntity<AvailabilitySlotResponseDTO> createAvailabilitySlot(@RequestBody AvailabilitySlotRequestDTO availabilitySlotRequestDTO) {
        AvailabilitySlotResponseDTO availabilitySlotResponseDTO = availabilitySlotService.createAvailabilitySlot(availabilitySlotRequestDTO);

        return ResponseEntity.ok().body(availabilitySlotResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailabilitySlot(@PathVariable Long id) {
        availabilitySlotService.deleteAvailabilitySlot(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilitySlotResponseDTO> updateAvailabilitySlot(@PathVariable Long id, @RequestBody AvailabilitySlotRequestDTO availabilitySlotRequestDTO) {
        AvailabilitySlotResponseDTO availabilitySlotResponseDTO = availabilitySlotService.updateAvailabilitySlot(id, availabilitySlotRequestDTO);
        return ResponseEntity.ok().body(availabilitySlotResponseDTO);
    }
}
