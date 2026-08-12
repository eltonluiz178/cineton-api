package dev.cineton.dto.seat.response;

import dev.cineton.domain.entities.Seat;
import dev.cineton.domain.entities.SessionSeat;
import dev.cineton.domain.enums.SessionSeatStatus;

import java.util.UUID;

public record SessionSeatResponse(
        UUID id,
        UUID seatId,
        String code,
        String row,
        Integer seatNumber,
        SessionSeatStatus status
) {
    public SessionSeatResponse(SessionSeat sessionSeat) {
        this(
                sessionSeat.getId(),
                sessionSeat.getSeat().getId(),
                sessionSeat.getSeat().getCode(),
                sessionSeat.getSeat().getRow(),
                sessionSeat.getSeat().getSeatNumber(),
                sessionSeat.getStatus()
        );
    }
}
