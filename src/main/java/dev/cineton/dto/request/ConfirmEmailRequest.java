package dev.cineton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ConfirmEmailRequest(
        @NotBlank(message = "email é necessário")
        @Email(message = "email inválido")
        String email,

        @NotBlank(message = "código de confirmação é necessário")
        @Size(min = 6, max = 6, message = "código deve ter 6 caracteres")
        String code
) {}
