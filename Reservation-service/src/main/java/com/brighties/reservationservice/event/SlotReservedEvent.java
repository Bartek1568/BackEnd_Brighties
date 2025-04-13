package com.brighties.reservationservice.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SlotReservedEvent {
    private Long teacherId;
    private String date;
    private String startTime;
    private String endTime;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



}
