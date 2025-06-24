package com.example.trabalhoDWC.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    @Column(name = "music_name", nullable = false)
    private String name;

    @Column(name = "music_songwriter")
    private String songwriter;

    @Column(name = "music_duration_seconds")
    private Long durationInSeconds;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
}