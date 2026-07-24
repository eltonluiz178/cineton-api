package dev.cineton.dto.request;

import dev.cineton.domain.enums.FilmStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateFilmRequest(

        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
        String title,

        String synopsis,

        @Min(value = 1, message = "A duração mínima do filme é 1 minuto")
        Integer durationMinutes,

        @Size(max = 10, message = "A classificação etária poder possuir no máximo 10 caracteres")
        String ageRating,

        @Past(message = "Data de lançamento é inválida")
        LocalDate releaseDate,

        String trailerUrl,

        FilmStatus status
) {}
