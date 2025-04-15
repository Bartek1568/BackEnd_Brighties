package com.brighties.emailsenderservice.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import teacher.TeacherRequest;
import teacher.TeacherServiceGrpc;
import teacher.TeacherInfoResponse;

@Component
public class TeacherGrpcClient {

    @GrpcClient("teacher-service")
    private TeacherServiceGrpc.TeacherServiceBlockingStub teacherStub;

    public TeacherInfoResponse getTeacherInfo(Long teacherId) {
        TeacherRequest request = TeacherRequest.newBuilder()
                .setTeacherId(teacherId)
                .build();

        return teacherStub.getTeacherInfo(request);
    }
}

