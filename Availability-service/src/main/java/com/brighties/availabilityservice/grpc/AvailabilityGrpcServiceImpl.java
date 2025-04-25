package com.brighties.availabilityservice.grpc;

import availability.AvailabilityServiceGrpc;
import availability.CheckSlotRequest;
import availability.SlotAvailabilityResponse;
import com.brighties.availabilityservice.repository.AvailabilitySlotRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class AvailabilityGrpcServiceImpl extends AvailabilityServiceGrpc.AvailabilityServiceImplBase {

    private final AvailabilitySlotRepository repository;

    @Autowired
    public AvailabilityGrpcServiceImpl(AvailabilitySlotRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkSlotAvailability(CheckSlotRequest request, StreamObserver<SlotAvailabilityResponse> responseObserver) {
        Long availabilityId = request.getAvailabilityId();

        boolean isAvailable = repository.isSlotAvailable(availabilityId);

        SlotAvailabilityResponse response = SlotAvailabilityResponse.newBuilder()
                .setAvailable(isAvailable)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
