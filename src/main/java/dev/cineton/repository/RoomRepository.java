package dev.cineton.repository;

import dev.cineton.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    boolean findByName (String name);
}
