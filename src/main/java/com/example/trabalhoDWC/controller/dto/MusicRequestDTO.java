package com.example.trabalhoDWC.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicRequestDTO {

    private String name;
    private String songwriter;
    private Long durationInSeconds;
    private Long albumId;
}
