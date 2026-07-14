package dev.cineton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "o email é necessário")
    @Email(message = "email inválido")
    String email,

    @NotBlank(message = "a senha é necessário")
    @Size(min = 8, message = "a senha não poder ter menos de 8 caracteres")
    String password
) {}
