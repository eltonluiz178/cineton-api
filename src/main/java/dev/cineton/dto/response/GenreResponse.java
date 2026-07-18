package dev.cineton.dto.response;

import dev.cineton.domain.entities.Genre;

import java.util.UUID;

public record GenreResponse(UUID id, String name, String description) {
    public GenreResponse(Genre genre){
        this(genre.getId(), genre.getName(), genre.getDescription());
    }
}
