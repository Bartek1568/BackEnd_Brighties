package com.brighties.userservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private Integer phoneNumber;
}
