package com.brighties.userservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeacherResponseDTO extends UserResponseDTO {

    private String description;
    private List<String> specializations;

}
