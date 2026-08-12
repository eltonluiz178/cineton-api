package dev.cineton.dto.session.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateSessionRequest(

        @NotNull(message = "O filme é necessário")
        UUID filmId,

        @NotNull(message = "A sala é necessária")
        UUID roomId,

        @NotNull(message = "Data de início é necessário")
        @Future(message = "Data de início inválida")
        OffsetDateTime startsAt,

        @NotNull(message = "Preço base é necessário")
        @DecimalMin(value = "0.0", message = "Preço base inválido")
        BigDecimal basePrice
) {}
