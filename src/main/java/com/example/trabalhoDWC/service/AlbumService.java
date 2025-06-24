package com.example.trabalhoDWC.service;

import com.example.trabalhoDWC.controller.dto.AlbumRequestDTO;
import com.example.trabalhoDWC.controller.dto.AlbumResponseDTO;
import com.example.trabalhoDWC.controller.dto.MusicResponseDTO;
import com.example.trabalhoDWC.entity.Album;
import com.example.trabalhoDWC.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumResponseDTO> findAll() {
        return albumRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AlbumResponseDTO findById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado com ID " + id));
        return toDTO(album);
    }

    public AlbumResponseDTO create(AlbumRequestDTO request) {
        Album album = new Album();
        album.setName(request.getName());
        album.setYear(request.getYear());
        Album saved = albumRepository.save(album);
        return toDTO(saved);
    }

    public AlbumResponseDTO update(AlbumRequestDTO request, Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado com ID " + id));
        album.setName(request.getName());
        album.setYear(request.getYear());
        Album updated = albumRepository.save(album);
        return toDTO(updated);
    }

    public void delete(Long id) {
        albumRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return albumRepository.existsById(id);
    }

    private AlbumResponseDTO toDTO(Album album) {
        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setId(album.getId());
        dto.setName(album.getName());
        dto.setYear(album.getYear());
        album.getMusics().forEach(music -> {
            MusicResponseDTO musicDTO = new MusicResponseDTO();
            musicDTO.setId(music.getId());
            musicDTO.setName(music.getName());
            musicDTO.setSongwriter(music.getSongwriter());
            musicDTO.setDurationInSeconds(music.getDurationInSeconds());
            dto.getMusics().add(musicDTO);
        });
        return dto;
    }
}