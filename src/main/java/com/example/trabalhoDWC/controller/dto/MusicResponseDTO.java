package com.example.trabalhoDWC.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicResponseDTO {

    private Long id;
    private String name;
    private String songwriter;
    private Long durationInSeconds;
    private String album;
}
