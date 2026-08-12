package dev.cineton.service.impl;

import dev.cineton.domain.entities.Film;
import dev.cineton.domain.entities.Room;
import dev.cineton.domain.entities.Session;
import dev.cineton.dto.session.request.CreateSessionRequest;
import dev.cineton.dto.session.request.UpdateSessionRequest;
import dev.cineton.dto.session.response.SessionResponse;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.exceptions.NotFoundException;
import dev.cineton.repository.FilmRepository;
import dev.cineton.repository.RoomRepository;
import dev.cineton.repository.SessionRepository;
import dev.cineton.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final FilmRepository filmRepository;
    private final RoomRepository roomRepository;

    private static final int SESSION_TOLERANCE_MINUTES = 30;

    @Override
    @Transactional
    public SessionResponse create(CreateSessionRequest request) {

        Film sessionFilm =  filmRepository.findById(request.filmId()).orElseThrow(() -> new NotFoundException("Filme não encontrado"));
        Room sessionRoom = roomRepository.findById(request.roomId()).orElseThrow(() -> new NotFoundException("Sala não encontrada"));

        if(sessionRepository.existsByRoomAndStartsAt(sessionRoom, request.startsAt())){
            throw new CreateEntityException("Horário indisponível");
        }

        OffsetDateTime sessionEnd = request.startsAt()
                .plusMinutes(sessionFilm.getDurationMinutes())
                .plusMinutes(SESSION_TOLERANCE_MINUTES);

        Session newSession = Session.builder()
                .film(sessionFilm)
                .room(sessionRoom)
                .startsAt(request.startsAt())
                .endsAt(sessionEnd)
                .basePrice(request.basePrice())
                .build();

        return new SessionResponse(sessionRepository.save(newSession));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponse> findAll() {
        return sessionRepository.findAll().stream().map(SessionResponse::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponse findById(UUID id) {
        return new SessionResponse(sessionRepository.findById(id).orElseThrow(() -> new NotFoundException("Sessão não encontrada")));
    }

    @Override
    public SessionResponse update(UUID id, UpdateSessionRequest request) {
        Session oldSession = sessionRepository.findById(id).orElseThrow(() -> new NotFoundException("Id da Sessão inválido"));

        if(request.startsAt() != null) {
            if(sessionRepository.existsByRoomAndStartsAt(oldSession.getRoom(), request.startsAt())){
                throw new CreateEntityException("Horário indisponível");
            }

            OffsetDateTime sessionEnd = request.startsAt()
                    .plusMinutes(oldSession.getFilm().getDurationMinutes())
                    .plusMinutes(SESSION_TOLERANCE_MINUTES);

            oldSession.setStartsAt(request.startsAt());
            oldSession.setEndsAt(sessionEnd);
        }

        if(request.basePrice() != null) oldSession.setBasePrice(request.basePrice());
        if(request.status() != null) oldSession.setStatus(request.status());

        return new SessionResponse(sessionRepository.save(oldSession));
    }

    @Override
    public void delete(UUID id) {
        sessionRepository.deleteById(id);
    }
}
