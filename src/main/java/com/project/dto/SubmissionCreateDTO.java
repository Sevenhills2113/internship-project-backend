package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionCreateDTO {

    @NotNull
    private Long internId;

    @NotBlank
    private String taskName;
    
    @NotBlank
    private String content;
}

