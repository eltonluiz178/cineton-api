package dev.cineton.service;

import dev.cineton.dto.session.request.CreateSessionRequest;
import dev.cineton.dto.session.request.UpdateSessionRequest;
import dev.cineton.dto.session.response.SessionResponse;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    SessionResponse create(CreateSessionRequest request);
    List<SessionResponse> findAll();
    SessionResponse findById(UUID id);
    SessionResponse update(UUID id, UpdateSessionRequest request);
    void delete(UUID id);
}
