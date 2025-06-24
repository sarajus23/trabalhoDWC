package com.example.trabalhoDWC.controller.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponseDTO {
    private Long id;
    private String name;

    @Temporal(TemporalType.DATE)
    private Date year;

    private List<MusicResponseDTO> musics = new ArrayList<>();
}
