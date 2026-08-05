package dev.cineton.service;

import dev.cineton.domain.entities.Room;
import dev.cineton.dto.seat.response.SeatResponse;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    void generateSeats(Room room);
    List<SeatResponse> findByRoom(UUID roomId);
    void deleteByRoom (Room room);
}
