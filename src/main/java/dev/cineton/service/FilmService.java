package dev.cineton.service;

import dev.cineton.dto.request.CreateFilmRequest;
import dev.cineton.dto.request.UpdateFilmRequest;
import dev.cineton.dto.response.FilmResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FilmService {
    FilmResponse create(CreateFilmRequest request);
    FilmResponse findById(UUID id);
    List<FilmResponse> findAll();
    FilmResponse update(UUID id, UpdateFilmRequest request);
    void delete(UUID id);
    FilmResponse uploadPoster(UUID id, MultipartFile file);
}
