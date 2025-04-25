package com.brighties.reservationservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import teacher.TeacherExistenceResponse;
import teacher.TeacherRequest;
import teacher.TeacherServiceGrpc;

@Service
public class TeacherGrpcClient {

    private final TeacherServiceGrpc.TeacherServiceBlockingStub teacherStub;

    public TeacherGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        teacherStub = TeacherServiceGrpc.newBlockingStub(channel);
    }

    public boolean checkTeacherExists(Long teacherId) {
        TeacherRequest request = TeacherRequest.newBuilder()
                .setTeacherId(teacherId)
                .build();

        TeacherExistenceResponse response = teacherStub.checkTeacherExistence(request);
        return response.getExists();
    }
}
