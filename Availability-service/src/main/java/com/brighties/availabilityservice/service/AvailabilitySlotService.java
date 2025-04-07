package com.brighties.availabilityservice.service;

import com.brighties.availabilityservice.dto.AvailabilitySlotRequestDTO;
import com.brighties.availabilityservice.dto.AvailabilitySlotResponseDTO;
import com.brighties.availabilityservice.mapper.AvailabilitySlotMapper;
import com.brighties.availabilityservice.model.AvailabilitySlot;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilitySlotService {

    private AvailabilitySlotRepository repository;

    public AvailabilitySlotService(AvailabilitySlotRepository repository) {
        this.repository = repository;
    }

    public List<AvailabilitySlotResponseDTO> getAvailabilityByTeacher(Long teacherId, LocalDate date) {
        List<AvailabilitySlot> slots = repository.findByTeacherIdAndDate(teacherId, date);

        return slots.stream().map(availabilitySlot -> AvailabilitySlotMapper.toDTO(availabilitySlot)).
                collect(Collectors.toList());
    }

    public AvailabilitySlotResponseDTO createAvailabilitySlot(Long teacherId, LocalDate date, AvailabilitySlotRequestDTO requestDTO) {
        AvailabilitySlot newAvailabilitySlot = repository.save(AvailabilitySlotMapper.toModel(requestDTO));

        return AvailabilitySlotMapper.toDTO(newAvailabilitySlot);
    }


}
