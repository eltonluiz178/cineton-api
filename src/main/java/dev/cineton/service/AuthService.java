package dev.cineton.service;

import dev.cineton.dto.request.LoginRequest;
import dev.cineton.dto.request.RegisterRequest;
import dev.cineton.dto.response.AuthResponse;

public interface AuthService {
    public AuthResponse registerUser(RegisterRequest registerRequestDto);

    public AuthResponse loginUser(LoginRequest loginRequestDto);
}
