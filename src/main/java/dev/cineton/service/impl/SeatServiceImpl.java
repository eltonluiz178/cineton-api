package dev.cineton.service.impl;

import dev.cineton.domain.entities.Room;
import dev.cineton.domain.entities.Seat;
import dev.cineton.dto.seat.response.SeatResponse;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.RoomRepository;
import dev.cineton.repository.SeatRepository;
import dev.cineton.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;

    @Override
    public void generateSeats(Room room) {
        int columns = 20;
        int rows = room.getCapacity() / columns;
        List<Seat> seats = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            String rowLetter = String.valueOf((char) ('A' + row));

            for (int col = 1; col <= columns; col++) {
                String code = rowLetter + col;

                seats.add(Seat.builder()
                        .room(room)
                        .code(code)
                        .row(rowLetter)
                        .seatNumber(col)
                        .build());
            }
        }

        seatRepository.saveAll(seats);
    }

    @Override
    public List<SeatResponse> findByRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Sala não encontrada"));

        return seatRepository.findByRoom(room).stream().map(SeatResponse::new).toList();
    }

    @Override
    public void deleteByRoom(Room room){
        seatRepository.deleteByRoom(room);
    }
}
