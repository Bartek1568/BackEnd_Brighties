package com.brighties.availabilityservice.controller;


import com.brighties.availabilityservice.dto.AvailabilitySlotRequestDTO;
import com.brighties.availabilityservice.dto.AvailabilitySlotResponseDTO;
import com.brighties.availabilityservice.service.AvailabilitySlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    public AvailabilitySlotController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AvailabilitySlotResponseDTO>> getAvailabilitySlotsByTeacher(@PathVariable Long teacherId, @PathVariable LocalDate date) {

        List<AvailabilitySlotResponseDTO> availabilitySlots = availabilitySlotService.getAvailabilityByTeacher(teacherId, date);

        return ResponseEntity.ok().body(availabilitySlots);
    }

    @PostMapping
    public ResponseEntity<AvailabilitySlotResponseDTO> createAvailabilitySlot(@RequestBody AvailabilitySlotRequestDTO availabilitySlotRequestDTO) {
        AvailabilitySlotResponseDTO availabilitySlotResponseDTO = availabilitySlotService.createAvailabilitySlot(availabilitySlotRequestDTO);

        return ResponseEntity.ok().body(availabilitySlotResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAvailabilitySlot(@RequestBody Long id) {
        availabilitySlotService.deleteAvailabilitySlot(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<AvailabilitySlotResponseDTO> updateAvailabilitySlot(@RequestBody Long id, @RequestBody AvailabilitySlotRequestDTO availabilitySlotRequestDTO) {
        AvailabilitySlotResponseDTO availabilitySlotResponseDTO = availabilitySlotService.updateAvailabilitySlot(id, availabilitySlotRequestDTO);
        return ResponseEntity.ok().body(availabilitySlotResponseDTO);
    }
}
