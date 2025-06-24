package com.example.trabalhoDWC.controller;

import com.example.trabalhoDWC.controller.dto.AlbumRequestDTO;
import com.example.trabalhoDWC.controller.dto.AlbumResponseDTO;
import com.example.trabalhoDWC.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AlbumResponseDTO>> findAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AlbumResponseDTO> findById(@PathVariable Long id) {
        if (!albumService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + id);
        }
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumResponseDTO> create(@RequestBody AlbumRequestDTO request) {
        AlbumResponseDTO response = albumService.create(request);
        URI location = URI.create("/album/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlbumResponseDTO> update(@RequestBody AlbumRequestDTO request, @PathVariable Long id) {
        if (!albumService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + id);
        }
        return ResponseEntity.ok(albumService.update(request, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!albumService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + id);
        }
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}