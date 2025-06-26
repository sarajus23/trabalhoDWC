package com.example.trabalhoDWC.controller;

import com.example.trabalhoDWC.controller.dto.AlbumRequestDTO;
import com.example.trabalhoDWC.controller.dto.AlbumResponseDTO;
import com.example.trabalhoDWC.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@Tag(name = "Álbuns", description = "Operações relacionadas ao gerenciamento de álbuns musicais")
@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(summary = "Listar todos os álbuns", description = "Retorna uma lista com todos os álbuns cadastrados.")
    @GetMapping("/findAll")
    public ResponseEntity<List<AlbumResponseDTO>> findAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @Operation(summary = "Buscar álbum por ID", description = "Retorna os detalhes de um álbum específico a partir do ID fornecido.")
    @GetMapping("/findById/{id}")
    public ResponseEntity<AlbumResponseDTO> findById(@PathVariable Long id) {
        if (!albumService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + id);
        }
        return ResponseEntity.ok(albumService.findById(id));
    }

    @Operation(summary = "Criar novo álbum", description = "Cria um novo álbum com os dados fornecidos no corpo da requisição.")
    @PostMapping("/create")
    public ResponseEntity<AlbumResponseDTO> create(@RequestBody AlbumRequestDTO request) {
        AlbumResponseDTO response = albumService.create(request);
        URI location = URI.create("/album/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Atualizar álbum existente", description = "Atualiza os dados de um álbum existente a partir do ID fornecido.")
    @PutMapping("/update/{id}")
    public ResponseEntity<AlbumResponseDTO> update(@RequestBody AlbumRequestDTO request, @PathVariable Long id) {
        if (!albumService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + id);
        }
        return ResponseEntity.ok(albumService.update(request, id));
    }

    @Operation(summary = "Deletar álbum", description = "Remove um álbum do sistema com base no ID fornecido.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!albumService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum não encontrado com ID " + id);
        }
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
