package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCreateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String program;

    @NotBlank
    private String deadline;

    @NotNull
    private Long mentorId;
    
    @NotNull
    private Long internId; 
}
