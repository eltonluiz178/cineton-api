package dev.cineton.repository;

import dev.cineton.domain.entities.Room;
import dev.cineton.domain.entities.Seat;
import dev.cineton.domain.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByRoom(Room room);
    void deleteByRoom(Room room);
}
