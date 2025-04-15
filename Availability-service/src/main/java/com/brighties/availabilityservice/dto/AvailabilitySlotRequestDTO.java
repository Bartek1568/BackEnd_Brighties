package com.brighties.availabilityservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilitySlotRequestDTO {

    @NotBlank
    private Long teacherId;

    @NotBlank
    private LocalDate date;

    @NotBlank
    private DayOfWeek dayOfWeek;

    @NotBlank
    private LocalTime startTime;

    @NotBlank
    private LocalTime endTime;

    @NotBlank
    private boolean isAvailable;

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean getAvailable() {
        return isAvailable;
    }


    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


}
