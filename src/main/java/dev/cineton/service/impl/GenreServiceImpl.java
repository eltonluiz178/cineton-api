package dev.cineton.service.impl;

import dev.cineton.domain.entities.Genre;
import dev.cineton.dto.request.CreateGenreRequest;
import dev.cineton.dto.response.GenreResponse;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.GenreRepository;
import dev.cineton.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreResponse create(CreateGenreRequest request) {

        if(genreRepository.existsByName(request.name())){
            throw new CreateEntityException("Já existe um gênero com este nome.");
        }

        Genre newGenre = Genre.builder()
                .name(request.name())
                .description(request.description())
                .build();

        return new GenreResponse(genreRepository.save(newGenre));
    }

    @Override
    public List<GenreResponse> findAll() {
        return genreRepository.findAll().stream().map(genre -> new GenreResponse(genre)).toList();
    }

    @Override
    public GenreResponse findById(UUID id) {
        return new GenreResponse(genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gênero não encontrado.")));
    }

    @Override
    public void delete(UUID id) {
        genreRepository.deleteById(id);
    }
}
