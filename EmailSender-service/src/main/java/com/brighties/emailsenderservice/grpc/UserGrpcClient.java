package com.brighties.emailsenderservice.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;
import user.*;

@Component
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

    public UserInfoByRoleResponse getUserInfoByRole(Long userId, String role) {
        UserRoleRequest request = UserRoleRequest.newBuilder()
                .setUserId(userId)
                .setExpectedRole(role)
                .build();

        return userStub.getUserInfoByRole(request);
    }
}

