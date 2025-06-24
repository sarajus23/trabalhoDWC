package com.example.trabalhoDWC.controller.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequestDTO {
    private String name;
    private Date year;
}
