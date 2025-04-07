package com.brighties.availabilityservice.mapper;

import com.brighties.availabilityservice.dto.AvailabilitySlotRequestDTO;
import com.brighties.availabilityservice.dto.AvailabilitySlotResponseDTO;
import com.brighties.availabilityservice.model.AvailabilitySlot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilitySlotMapper {

    public static AvailabilitySlotResponseDTO toDTO(AvailabilitySlot availabilitySlot) {
        AvailabilitySlotResponseDTO availabilitySlotDTO = new AvailabilitySlotResponseDTO();
        availabilitySlotDTO.setId(availabilitySlot.getId().toString());
        availabilitySlotDTO.setDate(availabilitySlot.toString());
        availabilitySlotDTO.setTeacherId(availabilitySlot.getTeacherId().toString());
        availabilitySlotDTO.setDayOfWeek(availabilitySlot.getDayOfWeek().toString());
        availabilitySlotDTO.setStartTime(availabilitySlot.getStartTime().toString());
        availabilitySlotDTO.setEndTime(availabilitySlot.getEndTime().toString());
        availabilitySlotDTO.setAvailable(String.valueOf(availabilitySlot.isAvailable()));

        return availabilitySlotDTO;
    }

    public static AvailabilitySlot toModel(AvailabilitySlotRequestDTO availabilitySlotDTO) {
        AvailabilitySlot availabilitySlot = new AvailabilitySlot();

        availabilitySlot.setTeacherId(Long.valueOf(availabilitySlotDTO.getTeacherId()));
        availabilitySlot.setDate(LocalDate.parse(availabilitySlotDTO.getDate()));
        availabilitySlot.setStartTime(LocalTime.parse(availabilitySlotDTO.getStartTime()));
        availabilitySlot.setEndTime(LocalTime.parse(availabilitySlotDTO.getEndTime()));
        availabilitySlot.setDayOfWeek(DayOfWeek.valueOf(availabilitySlotDTO.getDayOfWeek()));
        availabilitySlot.setAvailable(Boolean.valueOf(availabilitySlotDTO.getAvailable()));

        return availabilitySlot;


    }
}
