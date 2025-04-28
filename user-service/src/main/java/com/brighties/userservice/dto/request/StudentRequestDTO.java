package com.brighties.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDTO extends UserRequestDTO {


    @NotBlank(message = "Goal is required")
    private String goal;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Grade is required")
    private Integer grade;

    @NotBlank(message = "School type is required")
    private String schoolType;
}

