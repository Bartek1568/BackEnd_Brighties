package com.brighties.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherRequestDTO extends UserRequestDTO {

    @NotBlank(message = "description is required")
    private String description;

    @NotEmpty(message = "Specializations are required")
    private List<String> specializations;


}
