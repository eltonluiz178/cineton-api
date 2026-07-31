package dev.cineton.dto.room.response;

import dev.cineton.domain.entities.Room;

import java.util.UUID;

public record RoomResponse(
        UUID id,
        String name,
        Integer capacity
        // adicionar lista de assentos(Seat) e sessões(Session).
) {
    public RoomResponse(Room room){
        this(room.getId(), room.getName(), room.getCapacity());
    }
}
