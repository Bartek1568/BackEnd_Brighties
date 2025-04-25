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
        availabilitySlotDTO.setId(availabilitySlot.getId());
        availabilitySlotDTO.setDate(availabilitySlot.getDate());
        availabilitySlotDTO.setTeacherId(availabilitySlot.getTeacherId());
        availabilitySlotDTO.setDayOfWeek(availabilitySlot.getDayOfWeek());
        availabilitySlotDTO.setStartTime(availabilitySlot.getStartTime());
        availabilitySlotDTO.setEndTime(availabilitySlot.getEndTime());
        availabilitySlotDTO.setAvailable(availabilitySlot.isAvailable());
        availabilitySlotDTO.setReoccurringWeekly(availabilitySlot.isReoccurringWeekly());

        return availabilitySlotDTO;
    }

    public static AvailabilitySlot toModel(AvailabilitySlotRequestDTO availabilitySlotDTO) {
        AvailabilitySlot availabilitySlot = new AvailabilitySlot();

        availabilitySlot.setTeacherId(availabilitySlotDTO.getTeacherId());
        availabilitySlot.setDate(availabilitySlotDTO.getDate());
        availabilitySlot.setStartTime(availabilitySlotDTO.getStartTime());
        availabilitySlot.setEndTime(availabilitySlotDTO.getEndTime());
        availabilitySlot.setDayOfWeek(availabilitySlotDTO.getDayOfWeek());
        availabilitySlot.setAvailable(availabilitySlotDTO.isAvailable());
        availabilitySlot.setReoccurringWeekly(availabilitySlotDTO.isReoccurringWeekly());
        return availabilitySlot;


    }
}
