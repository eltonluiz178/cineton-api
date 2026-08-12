package dev.cineton.dto.session.response;

import dev.cineton.domain.entities.Session;
import dev.cineton.domain.enums.SessionStatus;
import dev.cineton.dto.film.response.FilmResponse;
import dev.cineton.dto.room.response.RoomResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SessionResponse(
        UUID id,
        FilmResponse film,
        RoomResponse room,
        OffsetDateTime startsAt,
        OffsetDateTime endsAt,
        BigDecimal basePrice,
        SessionStatus status
) {
    public SessionResponse(Session session) {
        this(
                session.getId(),
                new FilmResponse(session.getFilm()),
                new RoomResponse(session.getRoom()),
                session.getStartsAt(),
                session.getEndsAt(),
                session.getBasePrice(),
                session.getStatus()
        );
    }
}
