package dev.cineton.service;

import dev.cineton.domain.entities.Room;
import dev.cineton.dto.seat.response.SeatResponse;
import dev.cineton.dto.seat.response.SessionSeatResponse;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    void generateSeats(Room room);
    List<SeatResponse> findByRoomId(UUID roomId);
    List<SessionSeatResponse> findBySessionId(UUID sessionId);
    void deleteByRoom (Room room);
}
