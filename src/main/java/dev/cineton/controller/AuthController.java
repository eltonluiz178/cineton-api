package dev.cineton.controller;

import dev.cineton.dto.request.ConfirmEmailRequest;
import dev.cineton.dto.request.LoginRequest;
import dev.cineton.dto.request.RegisterRequest;
import dev.cineton.dto.response.AuthResponse;
import dev.cineton.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Validated
@Tag(name = "Auth", description = "Operações relacionadas a autenticação dos usuários")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realiza login do usuário", description = "Realiza login stateless do usuário a partir do email" +
            " e senha, retorna as informações do usuário")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest body) {
        return ResponseEntity.ok(authService.loginUser(body));
    }

    @PostMapping("/register")
    @Operation(summary = "O usuário pode se registrar", description = "Permite que o usuário consiga fazer o próprio registro")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest body) {
        return ResponseEntity.accepted().body(authService.registerUser(body));
    }

    @PostMapping("/confirm")
    @Operation(summary = "Confirma o email do usuário", description = "Atráves do código confirma conta do usuário pelo email")
    public ResponseEntity<AuthResponse> confirm(@Valid @RequestBody ConfirmEmailRequest body) {
        return ResponseEntity.ok(authService.confirmEmail(body));
    }
}
