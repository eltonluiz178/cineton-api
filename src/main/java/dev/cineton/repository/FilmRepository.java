package dev.cineton.repository;

import dev.cineton.domain.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilmRepository extends JpaRepository<Film, UUID> {
    boolean existsByTitle(String title);
}
