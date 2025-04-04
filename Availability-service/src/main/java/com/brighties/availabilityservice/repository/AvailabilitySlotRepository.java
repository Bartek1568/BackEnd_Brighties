package com.brighties.availabilityservice.repository;

import com.brighties.availabilityservice.model.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {

    List<AvailabilitySlot> findByTeacherIdAndDate(Long teacherId, LocalDate date);

}
