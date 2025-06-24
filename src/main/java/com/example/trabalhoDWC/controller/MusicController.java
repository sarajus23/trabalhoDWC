package com.example.trabalhoDWC.controller;

import com.example.trabalhoDWC.controller.dto.MusicRequestDTO;
import com.example.trabalhoDWC.controller.dto.MusicResponseDTO;
import com.example.trabalhoDWC.service.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<MusicResponseDTO>> findAll() {
        return ResponseEntity.ok(musicService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MusicResponseDTO> findById(@PathVariable Long id) {
        if (!musicService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada com ID " + id);
        }
        return ResponseEntity.ok(musicService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MusicResponseDTO> create(@RequestBody MusicRequestDTO request) {
        if (!musicService.albumExistsById(request.getAlbumId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + request.getAlbumId());
        }

        MusicResponseDTO response = musicService.create(request);
        URI location = URI.create("/music/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MusicResponseDTO> update(@RequestBody MusicRequestDTO request, @PathVariable Long id) {
        if (!musicService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada com ID " + id);
        }

        if (!musicService.albumExistsById(request.getAlbumId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + request.getAlbumId());
        }

        return ResponseEntity.ok(musicService.update(request, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!musicService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada com ID " + id);
        }
        musicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
