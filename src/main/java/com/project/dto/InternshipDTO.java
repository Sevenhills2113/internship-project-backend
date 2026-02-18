package com.project.dto;

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
public class InternshipDTO {

    private Long id;
    private String title;
    private String company;
    private Integer progress;  // ✔ Keep this

}

