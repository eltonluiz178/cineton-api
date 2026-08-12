package dev.cineton.repository;

import dev.cineton.domain.entities.Session;
import dev.cineton.domain.entities.SessionSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionSeatRepository extends JpaRepository<SessionSeat, UUID> {
    List<SessionSeat> findBySession(Session session);
}
