package dev.cineton.dto.session.request;

import dev.cineton.domain.enums.SessionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record UpdateSessionRequest(
        @Future(message = "Data de início inválida")
        OffsetDateTime startsAt,

        @DecimalMin(value = "0.0", message = "Preço base inválido")
        BigDecimal basePrice,

        SessionStatus status
) {}
