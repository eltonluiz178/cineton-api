package dev.cineton.service.impl;

import dev.cineton.domain.entities.Room;
import dev.cineton.dto.room.request.CreateRoomRequest;
import dev.cineton.dto.room.response.RoomResponse;
import dev.cineton.exceptions.BusinessException;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.RoomRepository;
import dev.cineton.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;


    @Override
    public RoomResponse create(CreateRoomRequest request) {
        if (roomRepository.findByName(request.name())) {
            throw new CreateEntityException("Já existe uma sala com o mesmo nome.");
        }

        Room newRoom = Room.builder()
                .name(request.name())
                .capacity(request.capacity())
                .build();

        return new RoomResponse(roomRepository.save(newRoom));
    }

    @Override
    public List<RoomResponse> findAll() {
        return roomRepository.findAll().stream().map(RoomResponse::new).toList();
    }

    @Override
    public RoomResponse findById(UUID id) {
        return new RoomResponse(roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Sala não encontrada")));
    }

    @Override
    public void delete(UUID id) {
        roomRepository.deleteById(id);
    }
}
