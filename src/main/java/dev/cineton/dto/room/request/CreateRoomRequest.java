package dev.cineton.dto.room.request;

import jakarta.validation.constraints.*;

public record CreateRoomRequest(
        @NotBlank(message = "o nome é necessário")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotNull
        @Min(value = 20, message = "Capacidade mínima é 20")
        @Max(value = 500, message = "Capacidade máxima é 500")
        Integer capacity
) {
    public CreateRoomRequest {
        if (capacity != null && capacity % 20 != 0) {
            throw new IllegalArgumentException("A capacidade deve ser múltiplo de 20.");
        }
    }
}
