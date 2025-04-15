package com.brighties.teacherservice.grpc;

import com.brighties.teacherservice.repository.TeacherRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import teacher.TeacherRequest;
import teacher.TeacherExistenceResponse;
import teacher.TeacherServiceGrpc;

@GrpcService
public class TeacherGrpcServiceImpl extends TeacherServiceGrpc.TeacherServiceImplBase {

    private final TeacherRepository teacherRepository;

    public TeacherGrpcServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void checkTeacherExistence(TeacherRequest request, StreamObserver<TeacherExistenceResponse> responseObserver) {
        boolean exists = teacherRepository.existsById(request.getTeacherId());

        TeacherExistenceResponse response = TeacherExistenceResponse.newBuilder()
                .setExists(exists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTeacherInfo(TeacherRequest request, StreamObserver<teacher.TeacherInfoResponse> responseObserver) {
        teacher.TeacherInfoResponse.Builder responseBuilder = teacher.TeacherInfoResponse.newBuilder();

        teacherRepository.findById(request.getTeacherId()).ifPresentOrElse(teacher -> {
            responseBuilder
                    .setTeacherId(teacher.getId())
                    .setEmail(teacher.getEmail())
                    .setPhone(String.valueOf(teacher.getPhoneNumber()))
                    .setName(teacher.getName())
                    .setSurname(teacher.getSurname());
        }, () -> {
        });

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

}

