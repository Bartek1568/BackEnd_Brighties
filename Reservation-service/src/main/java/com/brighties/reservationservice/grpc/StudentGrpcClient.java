package com.brighties.reservationservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import student.StudentExistenceResponse;
import student.StudentRequest;
import student.StudentServiceGrpc;

@Service
public class StudentGrpcClient {

    private final StudentServiceGrpc.StudentServiceBlockingStub studentStub;

    public StudentGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9093)
                .usePlaintext()
                .build();

        this.studentStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public boolean checkStudentExists(Long studentId) {
        StudentRequest request = StudentRequest.newBuilder()
                .setStudentId(studentId)
                .build();

        StudentExistenceResponse response = studentStub.checkStudentExistence(request);
        return response.getExists();
    }
}
