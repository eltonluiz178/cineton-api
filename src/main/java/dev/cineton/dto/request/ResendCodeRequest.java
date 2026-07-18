package dev.cineton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendCodeRequest(
        @NotBlank(message = "email necessário")
        @Email(message = "email inválido")
        String email
) {}
