package com.brighties.emailsenderservice.grpc;

import availability.AvailabilityServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import teacher.TeacherRequest;
import teacher.TeacherServiceGrpc;
import teacher.TeacherInfoResponse;

@Component
public class TeacherGrpcClient {

    private TeacherServiceGrpc.TeacherServiceBlockingStub teacherStub;

    public TeacherGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        this.teacherStub = TeacherServiceGrpc.newBlockingStub(channel);
    }

    public TeacherInfoResponse getTeacherInfo(Long teacherId) {
        TeacherRequest request = TeacherRequest.newBuilder()
                .setTeacherId(teacherId)

                .build();

        return teacherStub.getTeacherInfo(request);
    }
}

