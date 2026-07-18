package dev.cineton.service;

import dev.cineton.dto.request.CreateGenreRequest;
import dev.cineton.dto.response.GenreResponse;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    GenreResponse create(CreateGenreRequest request);
    List<GenreResponse> findAll();
    GenreResponse findById(UUID id);
    void delete(UUID id);
}
