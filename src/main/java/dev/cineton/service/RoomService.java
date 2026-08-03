package dev.cineton.service;

import dev.cineton.dto.room.request.CreateRoomRequest;
import dev.cineton.dto.room.response.RoomResponse;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    RoomResponse create(CreateRoomRequest request);
    List<RoomResponse> findAll();
    RoomResponse findById(UUID id);
    void delete(UUID id);
}
