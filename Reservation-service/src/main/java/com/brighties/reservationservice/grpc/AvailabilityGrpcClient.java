package com.brighties.reservationservice.grpc;

import availability.AvailabilityServiceGrpc;
import availability.CheckSlotRequest;
import availability.SlotAvailabilityResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AvailabilityGrpcClient {

    private final AvailabilityServiceGrpc.AvailabilityServiceBlockingStub availabilityStub;

    public AvailabilityGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9094)
                .usePlaintext()
                .build();

        this.availabilityStub = AvailabilityServiceGrpc.newBlockingStub(channel);
    }

    public boolean checkSlotAvailable(Long teacherId, LocalDate date, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        CheckSlotRequest request = CheckSlotRequest.newBuilder()
                .setTeacherId(teacherId)
                .setDate(String.valueOf(date))
                .setDayOfWeek(String.valueOf(dayOfWeek))
                .setStartTime(String.valueOf(startTime))
                .setEndTime(String.valueOf(endTime))
                .build();

        SlotAvailabilityResponse response = availabilityStub.checkSlotAvailability(request);
        return response.getAvailable();
    }
}

