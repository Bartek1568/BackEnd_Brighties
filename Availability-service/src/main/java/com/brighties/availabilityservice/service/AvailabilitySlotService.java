package com.brighties.availabilityservice.service;

import com.brighties.availabilityservice.dto.AvailabilitySlotRequestDTO;
import com.brighties.availabilityservice.dto.AvailabilitySlotResponseDTO;
import com.brighties.availabilityservice.mapper.AvailabilitySlotMapper;
import com.brighties.availabilityservice.model.AvailabilitySlot;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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

    public AvailabilitySlotResponseDTO createAvailabilitySlot( AvailabilitySlotRequestDTO requestDTO) {
        AvailabilitySlot newAvailabilitySlot = repository.save(AvailabilitySlotMapper.toModel(requestDTO));

        return AvailabilitySlotMapper.toDTO(newAvailabilitySlot);
    }

    public void deleteAvailabilitySlot(Long id){
        repository.deleteById(id);
    }

    public AvailabilitySlotResponseDTO updateAvailabilitySlot(Long id, AvailabilitySlotRequestDTO requestDTO) {
        Optional<AvailabilitySlot> availabilitySlot = repository.findById(id);

        availabilitySlot.get().setDate(LocalDate.parse(requestDTO.getDate()));
        availabilitySlot.get().setDayOfWeek(DayOfWeek.valueOf(requestDTO.getDayOfWeek()));
        availabilitySlot.get().setTeacherId(Long.valueOf(requestDTO.getTeacherId()));
        availabilitySlot.get().setStartTime(LocalTime.parse(requestDTO.getStartTime()));
        availabilitySlot.get().setEndTime(LocalTime.parse(requestDTO.getEndTime()));
        availabilitySlot.get().setAvailable(requestDTO.getAvailable());

        AvailabilitySlot savedAvailabilitySlot = repository.save(availabilitySlot.get());

        return AvailabilitySlotMapper.toDTO(savedAvailabilitySlot);
    }


}
