package com.brighties.reservationservice.event;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotReservedEvent {
    private Long teacherId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public SlotReservedEvent() {

    }

    public SlotReservedEvent(Long teacherId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.teacherId = teacherId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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