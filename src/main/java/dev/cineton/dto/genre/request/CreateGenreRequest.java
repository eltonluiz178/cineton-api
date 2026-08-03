package dev.cineton.dto.genre.request;

import jakarta.validation.constraints.NotBlank;

public record CreateGenreRequest(
        @NotBlank(message = "nome é necessário")
        String name,

        String description
) {}
