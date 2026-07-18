package dev.cineton.service.impl;

import dev.cineton.domain.entities.EmailConfirmation;
import dev.cineton.domain.entities.User;
import dev.cineton.domain.enums.UserStatus;
import dev.cineton.exceptions.AuthenticationException;
import dev.cineton.messaging.config.RabbitMQConfig;
import dev.cineton.messaging.events.UserRegisteredEvent;
import dev.cineton.repository.EmailConfirmationRepository;
import dev.cineton.service.EmailConfirmationService;
import dev.cineton.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final EmailConfirmationRepository emailConfirmationRepository;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public EmailConfirmation saveEmailConfirmation(User user, String confirmationCode) {
        ZoneOffset offset = ZoneId.of("America/Sao_Paulo").getRules().getOffset(java.time.Instant.now());
        OffsetDateTime now = OffsetDateTime.now(offset);

        EmailConfirmation emailConfirmation = EmailConfirmation.builder()
                .user(user)
                .code(confirmationCode)
                .expiresAt(now.plusHours(2))
                .build();

        return this.emailConfirmationRepository.save(emailConfirmation);
    }

    @Override
    public String codeGenerator() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int codeLength = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(codeLength);

        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Override
    public String generateEmailConfirmation(String userEmail) {

        User user = userService.getUserByEmail(userEmail);

        if (user.getStatus() != UserStatus.PENDING) throw new IllegalStateException("Não é possível gerar um código de confirmação para usuários com status diferente de pendente.");

        ZoneOffset offset = ZoneId.of("America/Sao_Paulo").getRules().getOffset(java.time.Instant.now());
        OffsetDateTime now = OffsetDateTime.now(offset);

        Optional<EmailConfirmation> oldEmailConfirmation = emailConfirmationRepository.findByUserEmail(user.getEmail());

        String newCode = codeGenerator();

        if (oldEmailConfirmation.isPresent()) {
            emailConfirmationRepository.delete(oldEmailConfirmation.get());
        }

        EmailConfirmation emailConfirmationSaved = saveEmailConfirmation(user, newCode);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.USER_REGISTERED_ROUTING_KEY,
                new UserRegisteredEvent(
                        user.getEmail(),
                        user.getName(),
                        emailConfirmationSaved.getCode(),
                        emailConfirmationSaved.getExpiresAt().toString()
                )
        );

        return "Solicitação recebida! Um código de confirmação está sendo enviado para o email: " + user.getEmail();
    }

    @Override
    public EmailConfirmation findByUserEmailAndCodeAndConfirmedAtIsNull(String email, String code) {
        userService.getUserByEmail(email);

        return this.emailConfirmationRepository.findByUserEmailAndCodeAndConfirmedAtIsNull(email, code).orElseThrow(() -> new AuthenticationException("Código inválido ou já utilizado."));
    }

    @Override
    public void confirmEmail(EmailConfirmation emailConfirmation) {
        this.emailConfirmationRepository.save(emailConfirmation);
    }
}
