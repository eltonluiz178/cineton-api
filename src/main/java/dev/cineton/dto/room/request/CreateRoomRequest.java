package dev.cineton.dto.room.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRoomRequest(
        @NotBlank(message = "o nome é necessário")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotNull(message = "a capacidade da sala é obrigatório")
        @Min(value = 1, message = "A capacidade mínima é 1")
        Integer capacity
) {}
