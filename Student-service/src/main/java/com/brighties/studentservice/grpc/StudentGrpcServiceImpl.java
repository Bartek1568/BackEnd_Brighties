package com.brighties.studentservice.grpc;

import com.brighties.studentservice.repository.StudentRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import student.StudentExistenceResponse;
import student.StudentRequest;
import student.StudentServiceGrpc;
import teacher.TeacherExistenceResponse;

@GrpcService
public class StudentGrpcServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    private final StudentRepository studentRepository;

    public StudentGrpcServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void checkStudentExistence(StudentRequest request, StreamObserver<StudentExistenceResponse> responseObserver){
        boolean exists = studentRepository.existsById(request.getStudentId());

        StudentExistenceResponse response = StudentExistenceResponse.newBuilder()
                .setExists(exists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentInfo(StudentRequest request, StreamObserver<student.StudentInfoResponse> responseObserver) {
        student.StudentInfoResponse.Builder responseBuilder = student.StudentInfoResponse.newBuilder();

        studentRepository.findById(request.getStudentId()).ifPresentOrElse(student -> {
            responseBuilder
                    .setExists(true)
                    .setEmail(student.getEmail())
                    .setName(student.getName())
                    .setSurname(student.getSurname())
                    .setPhone(student.getPhoneNumber());
        }, () -> {
            responseBuilder.setExists(false);
        });

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

}
