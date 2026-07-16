package dev.cineton.messaging.events;

public record UserRegisteredEvent(
        String email,
        String name,
        String code,
        String expiresAt
) {}
