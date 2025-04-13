package com.brighties.reservationservice.grpc;

import availability.AvailabilityServiceGrpc;
import availability.CheckSlotRequest;
import availability.SlotAvailabilityResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityGrpcClient {

    private final AvailabilityServiceGrpc.AvailabilityServiceBlockingStub availabilityStub;

    public AvailabilityGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9092)
                .usePlaintext()
                .build();

        this.availabilityStub = AvailabilityServiceGrpc.newBlockingStub(channel);
    }

    public boolean checkSlotAvailable(Long teacherId, String date, String startTime, String endTime) {
        CheckSlotRequest request = CheckSlotRequest.newBuilder()
                .setTeacherId(teacherId)
                .setDate(date)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .build();

        SlotAvailabilityResponse response = availabilityStub.checkSlotAvailability(request);
        return response.getAvailable();
    }
}

