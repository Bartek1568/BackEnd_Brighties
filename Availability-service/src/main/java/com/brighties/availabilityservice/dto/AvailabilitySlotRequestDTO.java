package com.brighties.availabilityservice.dto;

import jakarta.validation.constraints.NotBlank;

public class AvailabilitySlotRequestDTO {

    @NotBlank
    private String teacherId;

    @NotBlank
    private String date;

    @NotBlank
    private String dayOfWeek;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private boolean isAvailable;

    @NotBlank
    private boolean isReoccurring;

    @NotBlank
    private String reoccurringDate;

    public void setReoccurring(boolean reoccurring) {
        this.isReoccurring = reoccurring;
    }
    public boolean getIsReoccurring() {
        return isReoccurring;
    }

    public void setReoccurringDate(String reoccurringDate) {
        this.reoccurringDate = reoccurringDate;
    }
    public String getReoccurringDate() {
        return reoccurringDate;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean getAvailable() {
        return isAvailable;
    }


    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
