package com.brighties.availabilityservice.grpc;

import availability.AvailabilityServiceGrpc;
import availability.CheckSlotRequest;
import availability.SlotAvailabilityResponse;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@GrpcService
public class AvailabilityGrpcServiceImpl extends AvailabilityServiceGrpc.AvailabilityServiceImplBase {

    private final AvailabilitySlotRepository repository;

    @Autowired
    public AvailabilityGrpcServiceImpl(AvailabilitySlotRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkSlotAvailability(CheckSlotRequest request, StreamObserver<SlotAvailabilityResponse> responseObserver) {
        Long teacherId = request.getTeacherId();
        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime startTime = LocalTime.parse(request.getStartTime(), DateTimeFormatter.ISO_TIME);
        LocalTime endTime = LocalTime.parse(request.getEndTime(), DateTimeFormatter.ISO_TIME);

        boolean isAvailable = repository.isSlotAvailable(teacherId, date, startTime, endTime);

        SlotAvailabilityResponse response = SlotAvailabilityResponse.newBuilder()
                .setAvailable(isAvailable)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
