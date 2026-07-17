package dev.cineton.service;

public interface EmailService {
    void sendConfirmationEmail(String email, String name, String code, String expiresAt);
}
