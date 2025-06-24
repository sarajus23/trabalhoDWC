package com.example.trabalhoDWC.service;

import com.example.trabalhoDWC.controller.dto.MusicRequestDTO;
import com.example.trabalhoDWC.controller.dto.MusicResponseDTO;
import com.example.trabalhoDWC.entity.Album;
import com.example.trabalhoDWC.entity.Music;
import com.example.trabalhoDWC.repository.AlbumRepository;
import com.example.trabalhoDWC.repository.MusicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;

    public MusicService(MusicRepository musicRepository, AlbumRepository albumRepository) {
        this.musicRepository = musicRepository;
        this.albumRepository = albumRepository;
    }

    public List<MusicResponseDTO> findAll() {
        List<Music> musicList = musicRepository.findAll();

        return musicList.stream().map(music -> {
            MusicResponseDTO dto = new MusicResponseDTO();
            dto.setId(music.getId());
            dto.setName(music.getName());
            dto.setSongwriter(music.getSongwriter());
            dto.setDurationInSeconds(music.getDurationInSeconds());
            dto.setAlbum(music.getAlbum().getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public MusicResponseDTO findById(Long id) {
        MusicResponseDTO response = new MusicResponseDTO();

        musicRepository.findById(id).ifPresent(music -> {
            response.setId(music.getId());
            response.setName(music.getName());
            response.setSongwriter(music.getSongwriter());
            response.setDurationInSeconds(music.getDurationInSeconds());
            response.setAlbum(music.getAlbum().getName());
        });

        return response;
    }

    public MusicResponseDTO create(MusicRequestDTO request) {

        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new EntityNotFoundException("Album não encontrado com ID " + request.getAlbumId()));

        Music music = new Music();
        music.setName(request.getName());
        music.setSongwriter(request.getSongwriter());
        music.setDurationInSeconds(request.getDurationInSeconds());
        music.setAlbum(album);

        music = musicRepository.save(music);

        MusicResponseDTO response = new MusicResponseDTO();
        response.setId(music.getId());
        response.setName(music.getName());
        response.setSongwriter(music.getSongwriter());
        response.setDurationInSeconds(music.getDurationInSeconds());
        response.setAlbum(music.getAlbum().getName());

        return response;
    }

    public MusicResponseDTO update(MusicRequestDTO request, Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada com ID " + id));

        Optional.ofNullable(request.getName())
                .ifPresent(music::setName);

        Optional.ofNullable(request.getSongwriter())
                .ifPresent(music::setSongwriter);

        Optional.ofNullable(request.getDurationInSeconds())
                .ifPresent(music::setDurationInSeconds);

        Optional.ofNullable(request.getAlbumId())
                .ifPresent(albumId -> {
                    Album album = albumRepository.findById(albumId)
                            .orElseThrow(() -> new EntityNotFoundException("Album não encontrado com ID " + albumId));
                    music.setAlbum(album);
                });

        musicRepository.save(music);

        MusicResponseDTO response = new MusicResponseDTO();
        response.setId(music.getId());
        response.setName(music.getName());
        response.setSongwriter(music.getSongwriter());
        response.setDurationInSeconds(music.getDurationInSeconds());
        response.setAlbum(music.getAlbum() != null ? music.getAlbum().getName() : null);

        return response;
    }

    public void delete(Long id) {
        musicRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return musicRepository.existsById(id);
    }

    public boolean albumExistsById(Long id) {
        return albumRepository.existsById(id);
    }
}
