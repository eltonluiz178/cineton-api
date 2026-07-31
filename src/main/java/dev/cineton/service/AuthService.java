package dev.cineton.service;

import dev.cineton.dto.email.request.ConfirmEmailRequest;
import dev.cineton.dto.auth.request.LoginRequest;
import dev.cineton.dto.auth.request.RegisterRequest;
import dev.cineton.dto.auth.response.AuthResponse;

public interface AuthService {
    String registerUser(RegisterRequest registerRequestDto);

    AuthResponse loginUser(LoginRequest loginRequestDto);

    AuthResponse confirmEmail(ConfirmEmailRequest request);
}
