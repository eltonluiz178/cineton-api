package dev.cineton.controller;

import dev.cineton.dto.seat.response.SeatResponse;
import dev.cineton.dto.seat.response.SessionSeatResponse;
import dev.cineton.dto.session.request.CreateSessionRequest;
import dev.cineton.dto.session.request.UpdateSessionRequest;
import dev.cineton.dto.session.response.SessionResponse;
import dev.cineton.service.SeatService;
import dev.cineton.service.SessionService;
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
@RequestMapping("/sessions")
@Validated
@Tag(name = "Session", description = "Operações relacionadas as sessões")
public class SessionController {

    private final SessionService sessionService;
    private final SeatService seatService;

    @GetMapping
    @Operation(summary = "Retorna sessões", description = "Retorna uma lista com todas as sessões")
    public ResponseEntity<List<SessionResponse>> findAll(){ return ResponseEntity.ok(sessionService.findAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Sessão por id", description = "Retorna a sessão pelo id")
    public ResponseEntity<SessionResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(sessionService.findById(id));
    }

    @GetMapping("/{id}/seats")
    @Operation(summary = "Assentos da sessão", description = "Retorna uma lista com todos assentos pelo id da sessão")
    public ResponseEntity<List<SessionSeatResponse>> findSeatBySessionId(@PathVariable UUID id){
        return ResponseEntity.ok(seatService.findBySessionId(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma sessão", description = "Faz a criação de uma sessão")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<SessionResponse> create(@RequestBody @Valid CreateSessionRequest body){
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(body));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma sessão", description = "Faz a atualização de uma sessão existente")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<SessionResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateSessionRequest body){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(sessionService.update(id, body));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta sessão", description = "Faz a deleção de uma sessão apartir do id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        sessionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
