package dev.cineton.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateGenreRequest(
        @NotBlank(message = "nome é necessário")
        String name,

        String description
) {}
