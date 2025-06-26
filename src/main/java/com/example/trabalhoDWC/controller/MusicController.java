package com.example.trabalhoDWC.controller;

import com.example.trabalhoDWC.controller.dto.MusicRequestDTO;
import com.example.trabalhoDWC.controller.dto.MusicResponseDTO;
import com.example.trabalhoDWC.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@Tag(name = "Músicas", description = "Operações relacionadas ao gerenciamento de músicas")
@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @Operation(summary = "Listar todas as músicas", description = "Retorna uma lista com todas as músicas cadastradas no sistema.")
    @GetMapping("/findAll")
    public ResponseEntity<List<MusicResponseDTO>> findAll() {
        return ResponseEntity.ok(musicService.findAll());
    }

    @Operation(summary = "Buscar música por ID", description = "Retorna os detalhes de uma música a partir do ID fornecido.")
    @GetMapping("/findById/{id}")
    public ResponseEntity<MusicResponseDTO> findById(@PathVariable Long id) {
        if (!musicService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada com ID " + id);
        }
        return ResponseEntity.ok(musicService.findById(id));
    }

    @Operation(summary = "Criar nova música", description = "Cria uma nova música vinculada a um álbum existente.")
    @PostMapping("/create")
    public ResponseEntity<MusicResponseDTO> create(@RequestBody MusicRequestDTO request) {
        if (!musicService.albumExistsById(request.getAlbumId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + request.getAlbumId());
        }

        MusicResponseDTO response = musicService.create(request);
        URI location = URI.create("/music/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Atualizar música existente", description = "Atualiza os dados de uma música a partir do ID fornecido.")
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

    @Operation(summary = "Deletar música", description = "Remove uma música do sistema a partir do ID fornecido.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!musicService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada com ID " + id);
        }
        musicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
