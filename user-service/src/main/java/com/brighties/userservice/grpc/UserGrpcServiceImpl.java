package com.brighties.userservice.grpc;

import com.brighties.userservice.repository.StudentRepository;
import com.brighties.userservice.repository.TeacherRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import user.*;

@GrpcService
public class UserGrpcServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public UserGrpcServiceImpl(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void checkUserExistence(UserRequest request, StreamObserver<UserExistenceResponse> responseObserver) {
        boolean exists = teacherRepository.existsById(request.getUserId()) || studentRepository.existsById(request.getUserId());

        UserExistenceResponse response = UserExistenceResponse.newBuilder()
                .setExists(exists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfo(UserRequest request, StreamObserver<UserInfoResponse> responseObserver) {
        UserInfoResponse.Builder responseBuilder = UserInfoResponse.newBuilder();

        teacherRepository.findById(request.getUserId()).ifPresentOrElse(teacher -> {
            responseBuilder
                    .setExists(true)
                    .setEmail(teacher.getEmail())
                    .setName(teacher.getName())
                    .setSurname(teacher.getSurname())
                    .setPhone(teacher.getPhoneNumber())
                    .setRole("TEACHER");
        }, () -> {
            studentRepository.findById(request.getUserId()).ifPresentOrElse(student -> {
                responseBuilder
                        .setExists(true)
                        .setEmail(student.getEmail())
                        .setName(student.getName())
                        .setSurname(student.getSurname())
                        .setPhone(student.getPhoneNumber())
                        .setRole("STUDENT");
            }, () -> {
                responseBuilder.setExists(false);
            });
        });

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void checkUserExistenceByRole(UserRoleRequest request, StreamObserver<UserExistenceResponse> responseObserver) {
        Long userId = request.getUserId();
        String expectedRole = request.getExpectedRole().toUpperCase();

        boolean exists = switch (expectedRole) {
            case "TEACHER" -> teacherRepository.existsById(userId);
            case "STUDENT" -> studentRepository.existsById(userId);
            default -> false;
        };

        UserExistenceResponse response = UserExistenceResponse.newBuilder()
                .setExists(exists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfoByRole(UserRoleRequest request, StreamObserver<UserInfoByRoleResponse> responseObserver) {
        Long userId = request.getUserId();
        String expectedRole = request.getExpectedRole().toUpperCase();

        UserInfoByRoleResponse.Builder responseBuilder = UserInfoByRoleResponse.newBuilder();

        switch (expectedRole) {
            case "TEACHER" -> teacherRepository.findById(userId).ifPresentOrElse(teacher -> {
                responseBuilder
                        .setExists(true)
                        .setEmail(teacher.getEmail())
                        .setName(teacher.getName())
                        .setSurname(teacher.getSurname())
                        .setPhone(teacher.getPhoneNumber());
            }, () -> {
                responseBuilder.setExists(false);
            });
            case "STUDENT" -> studentRepository.findById(userId).ifPresentOrElse(student -> {
                responseBuilder
                        .setExists(true)
                        .setEmail(student.getEmail())
                        .setName(student.getName())
                        .setSurname(student.getSurname())
                        .setPhone(student.getPhoneNumber());
            }, () -> {
                responseBuilder.setExists(false);
            });
            default -> responseBuilder.setExists(false);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

}
