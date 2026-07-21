package dev.cineton.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateFilmRequest(
        @NotBlank(message = "o título é necessário")
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
        String title,

        String synopsis,

        @NotNull(message = "a duração do filme é obrigatório")
        @Min(value = 1, message = "A duração mínima do filme é 1")
        Integer durationMinutes,

        @Size(max = 10, message = "A classificação etária poder possuir no máximo 10 caracteres")
        String ageRating,

        @Past(message = "Data de lançamento é inválida")
        LocalDate releaseDate,

        String posterUrl,

        String trailerUrl
) {}
