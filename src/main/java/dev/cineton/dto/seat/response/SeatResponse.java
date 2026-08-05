package dev.cineton.dto.seat.response;

import dev.cineton.domain.entities.Seat;

import java.util.UUID;

public record SeatResponse(
        UUID id,
        String code,
        String row,
        Integer seatNumber
) {
    public SeatResponse(Seat seat){
        this(seat.getId(), seat.getCode(), seat.getRow(), seat.getSeatNumber());
    }
}
