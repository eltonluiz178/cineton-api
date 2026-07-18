package dev.cineton.controller;

import dev.cineton.dto.request.CreateGenreRequest;
import dev.cineton.dto.response.GenreResponse;
import dev.cineton.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
@Validated
@Tag(name = "Genre", description = "Operações relacionadas a genéros")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @Operation(summary = "Cria o genêro", description = "Faz a criação de genêro de filme")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<GenreResponse> create(@RequestBody @Valid CreateGenreRequest body){
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.create(body));
    }

    @GetMapping
    @Operation(summary = "Retorna genêros", description = "Retorna uma lista com todos os genêros de filme")
    public ResponseEntity<List<GenreResponse>> findAll(){
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Genêro por id", description = "Retorna o genêro pelo id")
    public ResponseEntity<GenreResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(genreService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta genêro", description = "Faz a deleção de um genêro apartir do id")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        genreService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
