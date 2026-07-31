package dev.cineton.controller;

import dev.cineton.dto.room.request.CreateRoomRequest;
import dev.cineton.dto.room.response.RoomResponse;
import dev.cineton.service.RoomService;
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
@RequestMapping("/rooms")
@Validated
@Tag(name = "Room", description = "Operações relacionadas a salas")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    @Operation(summary = "Retorna salas", description = "Retorna uma lista com todas as salas")
    public ResponseEntity<List<RoomResponse>> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Sala por id", description = "Retorna a sala pelo id")
    public ResponseEntity<RoomResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma sala", description = "Faz a criação de uma sala")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid CreateRoomRequest body){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(body));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta sala", description = "Faz a deleção de uma sala apartir do id")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        roomService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
