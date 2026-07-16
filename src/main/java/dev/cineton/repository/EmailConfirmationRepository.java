package dev.cineton.repository;

import dev.cineton.domain.entities.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, UUID> {
    Optional<EmailConfirmation> findByUserEmailAndCodeAndConfirmedAtIsNull(String email, String code);
}
