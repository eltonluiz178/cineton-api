package dev.cineton.repository;

import dev.cineton.domain.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    boolean existsByName(String name);
}
