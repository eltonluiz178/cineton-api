package dev.cineton.controller;

import dev.cineton.dto.request.CreateFilmRequest;
import dev.cineton.dto.request.UpdateFilmRequest;
import dev.cineton.dto.response.FilmResponse;
import dev.cineton.service.FilmService;
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
@RequestMapping("/films")
@Validated
@Tag(name = "Film", description = "Operações relacionadas a filmes")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    @Operation(summary = "Retorna filmes", description = "Retorna uma lista com todos os filmes")
    public ResponseEntity<List<FilmResponse>> findAll() {
        return ResponseEntity.ok(filmService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Filme por id", description = "Retorna o filme pelo id")
    public ResponseEntity<FilmResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(filmService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um filme", description = "Faz a criação de um filme")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<FilmResponse> create(@RequestBody @Valid CreateFilmRequest body){
        return ResponseEntity.status(HttpStatus.CREATED).body(filmService.create(body));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um filme", description = "Faz a atualização de um filme existente")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<FilmResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateFilmRequest body){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(filmService.update(id, body));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta filme", description = "Faz a deleção de um filme apartir do id")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        filmService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
