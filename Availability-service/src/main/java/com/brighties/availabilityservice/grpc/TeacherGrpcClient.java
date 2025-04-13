package com.brighties.availabilityservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import teacher.TeacherServiceGrpc;
import teacher.TeacherRequest;
import teacher.TeacherExistenceResponse;

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
