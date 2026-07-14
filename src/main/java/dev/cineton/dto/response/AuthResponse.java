package dev.cineton.dto.response;

import dev.cineton.domain.entities.User;
import dev.cineton.domain.enums.UserRole;

import java.util.UUID;

public record AuthResponse(UUID id, String email, UserRole role, String token) {
    public AuthResponse(User user, String acessToken) {
        this(user.getId(), user.getEmail(), user.getRole(), acessToken);
    }
}
