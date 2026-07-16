package dev.cineton.service;

import dev.cineton.dto.request.ConfirmEmailRequest;
import dev.cineton.dto.request.LoginRequest;
import dev.cineton.dto.request.RegisterRequest;
import dev.cineton.dto.response.AuthResponse;

public interface AuthService {
    String registerUser(RegisterRequest registerRequestDto);

    AuthResponse loginUser(LoginRequest loginRequestDto);

    AuthResponse confirmEmail(ConfirmEmailRequest request);
}
