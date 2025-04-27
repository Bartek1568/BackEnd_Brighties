package com.brighties.emailsenderservice.grpc;


import availability.AvailabilityServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import student.StudentRequest;
import student.StudentServiceGrpc;
import student.StudentInfoResponse;

@Component
public class StudentGrpcClient {

    private StudentServiceGrpc.StudentServiceBlockingStub studentStub;

    public StudentGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9093)
                .usePlaintext()
                .build();

        this.studentStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public StudentInfoResponse getStudentInfo(Long studentId) {
        StudentRequest request = StudentRequest.newBuilder()
                .setStudentId(studentId)
                .build();

        return studentStub.getStudentInfo(request);
    }
}
