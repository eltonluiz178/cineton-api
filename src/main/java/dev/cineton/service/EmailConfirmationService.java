package dev.cineton.service;

import dev.cineton.domain.entities.EmailConfirmation;
import dev.cineton.domain.entities.User;

public interface EmailConfirmationService {
    EmailConfirmation saveEmailConfirmation(User user, String confirmationCode);

    String codeGenerator();

    String generateEmailConfirmation(String userEmail);

    EmailConfirmation findByUserEmailAndCodeAndConfirmedAtIsNull(String email, String code);

    void confirmEmail(EmailConfirmation emailConfirmation);
}
