package dev.cineton.service.impl;

import dev.cineton.domain.entities.EmailConfirmation;
import dev.cineton.domain.entities.User;
import dev.cineton.domain.enums.UserRole;
import dev.cineton.domain.enums.UserStatus;
import dev.cineton.dto.request.ConfirmEmailRequest;
import dev.cineton.dto.request.LoginRequest;
import dev.cineton.dto.request.RegisterRequest;
import dev.cineton.dto.response.AuthResponse;
import dev.cineton.exceptions.AuthenticationException;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.messaging.config.RabbitMQConfig;
import dev.cineton.messaging.events.UserRegisteredEvent;
import dev.cineton.repository.UserRepository;
import dev.cineton.infra.security.JwtService;
import dev.cineton.infra.security.UserPrincipal;
import dev.cineton.service.AuthService;
import dev.cineton.service.EmailConfirmationService;
import dev.cineton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailConfirmationService emailConfirmationService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public String registerUser(RegisterRequest registerRequestDto) {
        if (this.userRepository.existsByEmail(registerRequestDto.email())) {
            throw new CreateEntityException("Já existe um cadastro com este Email!");
        }

        User newUser = User.builder()
                .name(registerRequestDto.name())
                .email(registerRequestDto.email())
                .password(passwordEncoder.encode(registerRequestDto.password()))
                .role(UserRole.CUSTOMER)
                .status(UserStatus.PENDING)
                .build();

        User user = this.userRepository.save(newUser);

        String codeConfirmation = this.emailConfirmationService.codeGenerator();

        EmailConfirmation emailConfirmationSaved = this.emailConfirmationService.saveEmailConfirmation(user, codeConfirmation);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.USER_REGISTERED_ROUTING_KEY,
                new UserRegisteredEvent(
                        user.getEmail(),
                        user.getName(),
                        codeConfirmation,
                        emailConfirmationSaved.getExpiresAt().toString()
                )
        );

        return "Enviamos um código de confirmação para o email: " + user.getEmail();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequestDto){
        User user = userService.getUserByEmail(loginRequestDto.email());

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new AuthenticationException("Credenciais de Login Inválidas!");
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateToken(userPrincipal);

        return new  AuthResponse(user, accessToken);
    }

    @Override
    @Transactional
    public AuthResponse confirmEmail(ConfirmEmailRequest request) {
        EmailConfirmation confirmation = emailConfirmationService
                .findByUserEmailAndCodeAndConfirmedAtIsNull(request.email(), request.code());

        if (confirmation.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new AuthenticationException("Código expirado.");
        }

        confirmation.setConfirmedAt(OffsetDateTime.now());
        emailConfirmationService.confirmEmail(confirmation);

        User user = confirmation.getUser();
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        UserPrincipal userPrincipal = new UserPrincipal(user);
        String accessToken = jwtService.generateToken(userPrincipal);
        return new AuthResponse(user, accessToken);
    }
}
