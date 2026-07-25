package dev.cineton.dto.response;

import dev.cineton.domain.entities.Film;
import dev.cineton.domain.enums.FilmStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FilmResponse(
        UUID id,
        String title,
        String synopsis,
        Integer durationMinutes,
        String ageRating,
        LocalDate releaseDate,
        String posterUrl,
        String trailerUrl,
        FilmStatus status,
        List<GenreResponse> genres
) {
    // Construtor para filme sem poster criado
    public FilmResponse(Film film) {
        this(
                film.getId(),
                film.getTitle(),
                film.getSynopsis(),
                film.getDurationMinutes(),
                film.getAgeRating(),
                film.getReleaseDate(),
                null,
                film.getTrailerUrl(),
                film.getStatus(),
                film.getGenres().stream().map(GenreResponse::new).toList());
    }
    // Construtor para filme com poster
    public FilmResponse(Film film, String posterUrl) {
        this(
                film.getId(),
                film.getTitle(),
                film.getSynopsis(),
                film.getDurationMinutes(),
                film.getAgeRating(),
                film.getReleaseDate(),
                posterUrl,
                film.getTrailerUrl(),
                film.getStatus(),
                film.getGenres().stream().map(GenreResponse::new).toList()
        );
    }
}
