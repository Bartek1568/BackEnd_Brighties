package com.brighties.reservationservice.event;

import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class ReservationCreatedEvent {
    private Long reservationId;
    private Long teacherId;
    private Long studentId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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



    public ReservationCreatedEvent(Long reservationId, Long teacherId, Long studentId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.reservationId = reservationId;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public ReservationCreatedEvent() {

    }
}

