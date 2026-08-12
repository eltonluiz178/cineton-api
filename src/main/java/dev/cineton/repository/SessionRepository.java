package dev.cineton.repository;

import dev.cineton.domain.entities.Room;
import dev.cineton.domain.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    boolean existsByRoomAndStartsAt(Room room, OffsetDateTime startsAt);
}