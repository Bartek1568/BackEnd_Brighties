package com.brighties.emailsenderservice.grpc;


import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import student.StudentRequest;
import student.StudentServiceGrpc;
import student.StudentInfoResponse;

@Component
public class StudentGrpcClient {

    @GrpcClient("student-service")
    private StudentServiceGrpc.StudentServiceBlockingStub studentStub;

    public StudentInfoResponse getStudentInfo(Long studentId) {
        StudentRequest request = StudentRequest.newBuilder()
                .setStudentId(studentId)
                .build();

        return studentStub.getStudentInfo(request);
    }
}
