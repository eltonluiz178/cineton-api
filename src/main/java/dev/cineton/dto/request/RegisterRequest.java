package dev.cineton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
   @NotBlank(message = "nome é necessário")
   @Size(min = 5, max = 100, message = "o nome deve conter entre 5 a 100 caracteres")
   String name,

   @NotBlank(message = "email é necessário")
   @Email(message = "email inválido")
   String email,

   @NotBlank(message = "senha é necessária")
   @Size(min = 8, message = "a senha não poder ter menos de 8 caracteres")
   String password
) {}
