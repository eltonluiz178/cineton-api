package dev.cineton.service.impl;

import dev.cineton.domain.entities.User;
import dev.cineton.domain.enums.UserRole;
import dev.cineton.dto.request.LoginRequest;
import dev.cineton.dto.request.RegisterRequest;
import dev.cineton.dto.response.AuthResponse;
import dev.cineton.exceptions.AuthenticationException;
import dev.cineton.exceptions.CreateEntityException;
import dev.cineton.repository.UserRepository;
import dev.cineton.security.JwtService;
import dev.cineton.security.UserPrincipal;
import dev.cineton.service.AuthService;
import dev.cineton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public AuthResponse registerUser(RegisterRequest registerRequestDto) {
        if (this.userRepository.existsByEmail(registerRequestDto.email())) {
            throw new CreateEntityException("Já existe um cadastro com este Email!");
        }

        User newUser = User.builder()
                .name(registerRequestDto.name())
                .email(registerRequestDto.email())
                .password(passwordEncoder.encode(registerRequestDto.password()))
                .role(UserRole.CUSTOMER)
                .build();

        User user = this.userRepository.save(newUser);

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateToken(userPrincipal);

        return new AuthResponse(user, accessToken);
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
}
