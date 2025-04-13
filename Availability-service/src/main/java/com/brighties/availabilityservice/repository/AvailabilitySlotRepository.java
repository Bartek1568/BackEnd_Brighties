package com.brighties.availabilityservice.repository;

import com.brighties.availabilityservice.model.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {

    List<AvailabilitySlot> findByTeacherIdAndDate(Long teacherId, LocalDate date);
    AvailabilitySlot findByTeacherIdAndDateAndStartTimeAndEndTime(Long teacherId, LocalDate date, LocalTime start, LocalTime end);


    @Query("SELECT COUNT(a) > 0 FROM AvailabilitySlot a " +
            "WHERE a.teacherId = :teacherId " +
            "AND a.date = :date " +
            "AND a.startTime = :startTime " +
            "AND a.endTime = :endTime " +
            "AND a.isAvailable = true")
    boolean isSlotAvailable(@Param("teacherId") Long teacherId,
                            @Param("date") LocalDate date,
                            @Param("startTime") LocalTime startTime,
                            @Param("endTime") LocalTime endTime);

}
