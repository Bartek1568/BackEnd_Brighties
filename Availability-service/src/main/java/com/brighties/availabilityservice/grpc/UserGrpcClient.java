package com.brighties.availabilityservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import user.UserExistenceResponse;
import user.UserRoleRequest;
import user.UserServiceGrpc;

@Service
public class UserGrpcClient {

    private final UserServiceGrpc.UserServiceBlockingStub userStub;

    public UserGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        this.userStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public boolean checkUserExistsByRole(Long userId, String role) {
        UserRoleRequest request = UserRoleRequest.newBuilder()
                .setUserId(userId)
                .setExpectedRole(role)
                .build();

        UserExistenceResponse response = userStub.checkUserExistenceByRole(request);
        return response.getExists();
    }
}
