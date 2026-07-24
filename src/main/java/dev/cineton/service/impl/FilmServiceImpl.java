package dev.cineton.service.impl;

import dev.cineton.domain.entities.Film;
import dev.cineton.dto.request.CreateFilmRequest;
import dev.cineton.dto.request.UpdateFilmRequest;
import dev.cineton.dto.response.FilmResponse;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.FilmRepository;
import dev.cineton.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public FilmResponse create(CreateFilmRequest request){
        if(filmRepository.existsByTitle(request.title())){
            throw new CreateEntityException("Já existe um filme com este título.");
        }

        Film newFilm = Film.builder()
                .title(request.title())
                .synopsis(request.synopsis())
                .durationMinutes(request.durationMinutes())
                .ageRating(request.ageRating())
                .releaseDate(request.releaseDate())
                .trailerUrl(request.trailerUrl())
                .build();

        return new FilmResponse(filmRepository.save(newFilm));
    }

    @Override
    @Transactional(readOnly = true)
    public FilmResponse findById(UUID id) {
        return new FilmResponse(filmRepository.findById(id).orElseThrow(() -> new NotFoundException("Filme não encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmResponse> findAll() {
        return filmRepository.findAll().stream().map(FilmResponse::new).toList();
    }

    @Override
    public FilmResponse update(UUID id, UpdateFilmRequest request) {
        Film oldFilm = filmRepository.findById(id).orElseThrow(() -> new NotFoundException("Id do filme inválido"));

        if(request.title() != null && !request.title().equals(oldFilm.getTitle()) && filmRepository.existsByTitle(request.title())){
            throw new CreateEntityException("Já existe um filme com este título.");
        }

        if (request.title() != null) oldFilm.setTitle(request.title());
        if (request.synopsis() != null) oldFilm.setSynopsis(request.synopsis());
        if (request.durationMinutes() != null) oldFilm.setDurationMinutes(request.durationMinutes());
        if (request.ageRating() != null) oldFilm.setAgeRating(request.ageRating());
        if (request.releaseDate() != null) oldFilm.setReleaseDate(request.releaseDate());
        if (request.trailerUrl() != null) oldFilm.setTrailerUrl(request.trailerUrl());
        if (request.status() != null) oldFilm.setStatus(request.status());

        return new FilmResponse(filmRepository.save(oldFilm));
    }

    @Override
    public void delete(UUID id) {
        filmRepository.deleteById(id);
    }
}
